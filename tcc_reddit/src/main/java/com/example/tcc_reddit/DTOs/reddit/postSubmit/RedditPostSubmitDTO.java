package com.example.tcc_reddit.DTOs.reddit.postSubmit;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditPostSubmitDTO {
    private String success;
}
