package com.example.tcc_reddit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subreddit_post_arquivo_bruto")
public class SubRedditPostArquivoBruto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subreddit_id", referencedColumnName = "id")
    private SubReddit subreddit_id;

    @ManyToOne
    @JoinColumn(name = "municipio_id", referencedColumnName = "geocodigo")
    private Municipio municipio_id;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubredditpostCategoriaArquivoBruto> categorias = new HashSet<>();

    @Column(nullable = false)
    private String postId;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String selftext;
    @Column(nullable = true)
    private String name;
    @Column(nullable = true)
    private String author_id; //author_fullname do DTO
    @Column(nullable = true)
    private String author;
    @Column(nullable = true)
    private boolean saved;
    @Column(nullable = true)
    private String subreddit_name_prefixed;
    @Column(nullable = false, columnDefinition = "TEXT")
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
    @Column(nullable = true, columnDefinition = "TEXT")
    private String url;
    @Column(nullable = true)
    private boolean over_18;
    @Column(nullable = true)
    private String created_utc;
    @Column(nullable = true)
    private String edited_at;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    public SubRedditPostArquivoBruto() {}

    public SubRedditPostArquivoBruto(String postID, SubReddit subreddit_id, String selftext, String author_fullname, String author, boolean saved, String subreddit_name_prefixed, String title, float upvote_ratio, int ups, int downs, int score, String created, int num_comments, String url, boolean over_18, String created_utc, String edited_at) {
        this.postId = postID;
        this.subreddit_id = subreddit_id;
        this.selftext = selftext;
        this.author_id = author_fullname;
        this.author = author;
        this.saved = saved;
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
