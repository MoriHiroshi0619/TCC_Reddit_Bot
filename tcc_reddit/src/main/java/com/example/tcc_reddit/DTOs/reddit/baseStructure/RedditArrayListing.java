package com.example.tcc_reddit.DTOs.reddit.baseStructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RedditArrayListing {
    @JsonProperty()
    private List<RedditListingDTO> redditListingDTOS;
}
