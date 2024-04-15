package com.javaunit3.springmvc.services;

import com.javaunit3.springmvc.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BestMovieService {

    private Movie movie;

    public Movie getBestMovie() {
        return movie;
    }

    @Autowired
    public BestMovieService(@Qualifier("titanicMovie")Movie movie) {
        this.movie = movie;
    }
}
