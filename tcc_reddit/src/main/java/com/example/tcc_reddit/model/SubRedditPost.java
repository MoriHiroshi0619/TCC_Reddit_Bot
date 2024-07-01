package com.example.tcc_reddit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subreddit_post")
public class SubRedditPost {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "subreddit_id", referencedColumnName = "id")
    private SubReddit subreddit_id;

    @ManyToOne
    @JoinColumn(name = "municipio_id", referencedColumnName = "geocodigo")
    private Municipio municipio_id;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubredditPostCategoria> categorias = new HashSet<>();

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String selftext;
    @Column(nullable = false)
    private String author_id; //author_fullname do DTO
    @Column(nullable = false)
    private String author;
    @Column(nullable = true)
    private boolean saved;
    @Column(nullable = true)
    private boolean approved;
    @Column(nullable = true)
    private String approved_at_utc;
    @Column(nullable = true)
    private String approved_by;
    @Column(nullable = true)
    private String subreddit_name_prefixed;
    @Column(nullable = false)
    private String title;
    @Column(nullable = true)
    private float upvote_ratio;
    @Column(nullable = true)
    private int ups;
    @Column(nullable = true)
    private int downs;
    @Column(nullable = true)
    private int score;
    @Column(nullable = true)
    private String created;
    @Column(nullable = true)
    private int num_comments;
    @Column(nullable = true)
    private String url;
    @Column(nullable = true)
    private boolean over_18;
    @Column(nullable = true)
    private String created_utc;
    @Column(nullable = true)
    private String edited_at;

    //contrutor padrão
    public SubRedditPost() {}

    public SubRedditPost(String id, SubReddit subreddit_id, String selftext, String author_fullname, String author, boolean saved, boolean approved, String approved_at_utc, String approved_by, String subreddit_name_prefixed, String title, float upvote_ratio, int ups, int downs, int score, String created, int num_comments, String url, boolean over_18, String created_utc, String edited_at) {
        this.id = id;
        this.subreddit_id = subreddit_id;
        this.selftext = selftext;
        this.author_id = author_fullname;
        this.author = author;
        this.saved = saved;
        this.approved = approved;
        this.approved_at_utc = approved_at_utc;
        this.approved_by = approved_by;
        this.subreddit_name_prefixed = subreddit_name_prefixed;
        this.title = title;
        this.upvote_ratio = upvote_ratio;
        this.ups = ups;
        this.downs = downs;
        this.score = score;
        this.created = created;
        this.num_comments = num_comments;
        this.url = url;
        this.over_18 = over_18;
        this.created_utc = created_utc;
        this.edited_at = edited_at;
    }
}
