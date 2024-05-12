package com.example.tcc_reddit.DTOs.reddit.postComments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RedditCommentDataDTO {
    private String subreddit_id;
    private String subreddit;
    private String id;
    private String author;
    private float created_utc;
    private boolean send_replies;
    private String parent_id;
    private int score;
    private String author_fullname;
    private String body;
    private boolean edited;
    private String name;
    private int downs;
    private int ups;
    private String permalink;
    private float created;
    private int depth;
}
