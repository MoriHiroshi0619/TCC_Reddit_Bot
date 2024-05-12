package com.example.tcc_reddit.DTOs.reddit.baseStructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RedditDataDTO {
    private String after;
    private int dist;
    private String modhash;
    private String geo_filter;
    private List<RedditChildDTO> children;
    private String before;

}
