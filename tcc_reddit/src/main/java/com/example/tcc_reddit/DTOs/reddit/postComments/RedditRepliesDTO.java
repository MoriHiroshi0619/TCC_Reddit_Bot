package com.example.tcc_reddit.DTOs.reddit.postComments;

import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditDataDTO;
import com.example.tcc_reddit.DTOs.reddit.postWatch.RedditPostDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RedditRepliesDTO {
    private String kind;
    private RedditDataDTO data;
}
