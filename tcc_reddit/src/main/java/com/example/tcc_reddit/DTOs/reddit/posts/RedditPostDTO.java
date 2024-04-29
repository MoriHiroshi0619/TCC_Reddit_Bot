package com.example.tcc_reddit.DTOs.reddit.posts;

import com.example.tcc_reddit.DTOs.JsonMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditPostDTO {
    private String kind;
    private RedditPostDataDTO data;

    public static RedditPostDTO fromJson(String json) throws JsonProcessingException {
        return JsonMapper.getObjectMapper().readValue(json, RedditPostDTO.class);
    }
}
