package com.example.tcc_reddit.DTOs.reddit.baseStructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RedditListingDTO {
    private String kind;
    private RedditDataDTO data;
}
