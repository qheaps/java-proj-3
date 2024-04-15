package com.javaunit3.springmvc;

import javax.persistence.*;

@Entity
@Table(name = "votes")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vote_id;

    @Column(name = "voter_name")
    private String voter_name;

    public int getVote_id() {
        return vote_id;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }

    public String getVoter_name() {
        return voter_name;
    }

    public void setVoter_name(String voter_name) {
        this.voter_name = voter_name;
    }
}
