package com.example.tcc_reddit.DTOs.reddit.subReddit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubRedditDataDTO{
    private String id;
    private String display_name;
    private String title;
    private String description;
}
