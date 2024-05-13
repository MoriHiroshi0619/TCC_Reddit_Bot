package com.example.tcc_reddit.DTOs.reddit.postComments;

import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditListingDTO;
import com.example.tcc_reddit.DTOs.reddit.postWatch.RedditPostDTO;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RedditCommentDataDTO {
    private String subreddit_id;
    private String subreddit;
    private String id;
    private String author;
    private float created_utc;
    private boolean send_replies;
    private String parent_id;
    private int score;
    private String author_fullname;
    private String body;
    private boolean edited;
    private String name;
    private int downs;
    private int ups;
    private String permalink;
    private float created;
    private int depth;

    private RedditListingDTO replie;

    @JsonSetter("replies")
    public void setReplies(JsonNode replies) {
        ObjectMapper mapper = new ObjectMapper();
         if (replies.isObject()) {
            try {
                this.replie = mapper.treeToValue(replies, RedditListingDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
