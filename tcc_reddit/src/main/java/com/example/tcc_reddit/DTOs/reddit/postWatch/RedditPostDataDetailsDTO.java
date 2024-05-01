package com.example.tcc_reddit.DTOs.reddit.postWatch;

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
    private String author;
    private boolean saved;
    private boolean edited;
    private boolean approved;
    private String approved_at_utc;
    private String approved_by;
    private String subreddit_name_prefixed;
    private String title;
    private float upvote_ratio;
    private int ups;
    private int score;
    private float created;
    private String id;
    private int num_comments;
    private String url;
    private boolean over_18;
    private float created_utc;

}
