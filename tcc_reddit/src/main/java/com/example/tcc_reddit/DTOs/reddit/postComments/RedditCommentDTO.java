package com.example.tcc_reddit.DTOs.reddit.postComments;

import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditChildDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("t1")
@Component
public class RedditCommentDTO extends RedditChildDTO {
    private RedditCommentDataDTO data;
}
