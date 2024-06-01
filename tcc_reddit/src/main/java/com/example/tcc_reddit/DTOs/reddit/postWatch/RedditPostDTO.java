package com.example.tcc_reddit.DTOs.reddit.postWatch;

import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditChildDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
@JsonTypeName("t3")
public class RedditPostDTO extends RedditChildDTO{
    private RedditPostDataDTO data;
}
