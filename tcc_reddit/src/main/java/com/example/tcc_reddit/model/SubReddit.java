package com.example.tcc_reddit.model;

import jakarta.persistence.*;
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

    @Column(nullable = false, unique = true)
    private String subRedditName;

    @Column(nullable = true)
    private String after;

    @Column(nullable = true)
    private String before;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;

    public SubReddit() {}
    public SubReddit(String id, String subRedditName) {
        this.id = id;
        this.subRedditName = subRedditName;
    }

    public SubReddit(String id, String subRedditName, String title, String description) {
        this.id = id;
        this.subRedditName = subRedditName;
        this.title = title;
        this.description = description;
    }

}
