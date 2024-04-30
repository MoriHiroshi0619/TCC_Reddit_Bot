package com.example.tcc_reddit.DTOs.reddit.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditPostDTO {
    private String kind;
    private RedditPostDataDTO data;

}
