package com.example.tcc_reddit.DTOs.reddit.postSubmit;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RedditPostSubmitDTO {
    private String success;
}
