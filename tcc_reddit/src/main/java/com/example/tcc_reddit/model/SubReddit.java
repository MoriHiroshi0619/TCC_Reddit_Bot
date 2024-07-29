package com.example.tcc_reddit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "subreddit_subreddit")
public class SubReddit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subRedditId;

    @Column(nullable = false, unique = true)
    private String subRedditName;

    @Column(nullable = true)
    private String after;

    @Column(nullable = true)
    private String before;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private boolean acabou_after;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;


    public SubReddit() {}
    public SubReddit(String id, String subRedditName) {
        this.subRedditId = id;
        this.subRedditName = subRedditName;
        this.acabou_after = false;
    }

    public SubReddit(String id, String subRedditName, String title, String description) {
        this.subRedditId = id;
        this.subRedditName = subRedditName;
        this.title = title;
        this.description = description;
        this.acabou_after = false;
    }

}
