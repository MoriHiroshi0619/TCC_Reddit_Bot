package com.example.tcc_reddit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria_id;

    private String selftext;
    private String author_id; //author_fullname do DTO
    private String author;
    private boolean saved;
    private boolean approved;
    private String approved_at_utc;
    private String approved_by;
    private String subreddit_name_prefixed;
    private String title;
    private float upvote_ratio;
    private int ups;
    private int downs;
    private int score;
    private String created;
    private int num_comments;
    private String url;
    private boolean over_18;
    private String created_utc;
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
