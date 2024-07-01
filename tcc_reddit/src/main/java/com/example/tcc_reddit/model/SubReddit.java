package com.example.tcc_reddit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "subreddit_subreddit")
public class SubReddit{
    @Id
    private String id;

    @Column(nullable = false)
    private String subRedditName;

    @Column(nullable = true)
    private String after;

    @Column(nullable = true)
    private String before;



    public SubReddit() {}
    public SubReddit(String id, String subRedditName) {
        this.id = id;
        this.subRedditName = subRedditName;
    }

}
