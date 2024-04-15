package com.javaunit3.springmvc;

import com.javaunit3.springmvc.services.BestMovieService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class MovieController {
    @Autowired
    private BestMovieService bestMovieService;

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping("/addMovieForm")
    public String addMovieForm() {
        return "addMovie";
    }

    public String getIndexPage() {
        return "index";
    }

    @RequestMapping("/addMovie")
    public String addMovie(HttpServletRequest request) {
        String genre = request.getParameter("genre");
        String maturity = request.getParameter("maturityRating");
        String title = request.getParameter("movieTitle");

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setGenre(genre);
        movieEntity.setMaturity_rating(maturity);
        movieEntity.setTitle(title);

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(movieEntity);
        session.getTransaction().commit();

        return "addMovie";
    }

    @RequestMapping("/bestMovie")
    public String getBestMoviePage(Model model) {
       Session session = sessionFactory.getCurrentSession();
       session.beginTransaction();

       List<MovieEntity> movieEntityList = session.createQuery("from MovieEntity").list();
       movieEntityList.sort(Comparator.comparing(movieEntity -> movieEntity.getVotes().size()));

       MovieEntity bestMovie = movieEntityList.get(movieEntityList.size() - 1);

       List<String> voterNames = new ArrayList<>();
       for (VoteEntity vote: bestMovie.getVotes()) {
           voterNames.add(vote.getVoter_name());
       }

       String voterNamesList = String.join(",", voterNames);

       model.addAttribute("bestMovie", bestMovie.getTitle());
       model.addAttribute("bestMovieVoters", voterNamesList);

       session.getTransaction().commit();

       return "bestMovie";
    }

    @RequestMapping("/voteForBestMovieForm")
    public String voteForBestMovieFormPage(Model model) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        List<MovieEntity> movieEntityList = session.createQuery("from MovieEntity").list();
        session.getTransaction().commit();

        model.addAttribute("movies", movieEntityList);

        return "voteForBestMovie";
    }

    @RequestMapping("/voteForBestMovie")
    public String voteForBestMovie(HttpServletRequest request) {
        String movieId = request.getParameter("movieId");
        String voterName = request.getParameter("voterName");

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        MovieEntity movieEntity = (MovieEntity) session.get(MovieEntity.class, Integer.parseInt(movieId));
        VoteEntity vote = new VoteEntity();
        vote.setVoter_name(voterName);
        movieEntity.addVote(vote);

        session.update(movieEntity);

        session.getTransaction().commit();

        return "voteForBestMovie";
    }
}
