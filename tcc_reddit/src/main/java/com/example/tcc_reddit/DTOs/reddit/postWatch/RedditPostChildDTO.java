package com.example.tcc_reddit.DTOs.reddit.postWatch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditPostChildDTO {
    private String kind;
    private RedditPostDataDetailsDTO data;

}
