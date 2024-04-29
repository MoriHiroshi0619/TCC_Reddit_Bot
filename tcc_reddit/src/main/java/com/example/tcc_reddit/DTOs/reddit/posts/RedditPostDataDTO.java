package com.example.tcc_reddit.DTOs.reddit.posts;

import com.example.tcc_reddit.DTOs.JsonMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    public static RedditPostDataDTO fromJson(String json) throws JsonProcessingException {
        return JsonMapper.getObjectMapper().readValue(json, RedditPostDataDTO.class);
    }
}
