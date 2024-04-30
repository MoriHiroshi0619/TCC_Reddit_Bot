package com.example.tcc_reddit.DTOs.reddit.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditPostDataDetailsDTO {
    private String subreddit;
    private String selftext;
    private String author_fullname;
    private boolean saved;
    private String subreddit_name_prefixed;
    private String title;
    private float upvote_ratio;
    private int ups;
    private int score;
    private float created;
    private String id;
    private int num_comments;
    private String url;
    private float created_utc;

}
