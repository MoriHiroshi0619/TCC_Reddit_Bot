package com.example.tcc_reddit.DTOs.reddit.baseStructure;

import com.example.tcc_reddit.DTOs.reddit.postComments.RedditCommentDTO;
import com.example.tcc_reddit.DTOs.reddit.postWatch.RedditPostDTO;
import com.example.tcc_reddit.DTOs.reddit.subReddit.SubRedditDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import org.springframework.stereotype.Component;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "kind", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RedditPostDTO.class, name = "t3"),
        @JsonSubTypes.Type(value = RedditCommentDTO.class, name = "t1"),
        @JsonSubTypes.Type(value = SubRedditDTO.class, name = "t5")
})
abstract public class RedditChildDTO {

}

