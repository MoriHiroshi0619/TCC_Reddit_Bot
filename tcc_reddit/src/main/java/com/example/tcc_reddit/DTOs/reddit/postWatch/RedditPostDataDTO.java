package com.example.tcc_reddit.DTOs.reddit.postWatch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditPostDataDTO {
    private String after;
    private int dist;
    private String modhash;
    private String geo_filter;
    private List<RedditPostChildDTO> children;
    private String before;

}
