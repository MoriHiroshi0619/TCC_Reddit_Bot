package com.example.tcc_reddit.DTOs.reddit.postComments;

import com.example.tcc_reddit.DTOs.CustomUnixTimeDeserializer;
import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditListingDTO;
import com.example.tcc_reddit.DTOs.reddit.postWatch.RedditPostDTO;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RedditCommentDataDTO {
    //@todo corrigir o campo "edit", edit ? float : false
    private String subreddit_id;
    private String subreddit;
    private String id;
    private String author;
    @JsonDeserialize( using = CustomUnixTimeDeserializer.class)
    private String created_utc;
    private boolean send_replies;
    private String parent_id;
    private int score;
    private String author_fullname;
    private String body;
    private String name;
    private int downs;
    private int ups;
    private String permalink;
    @JsonDeserialize( using = CustomUnixTimeDeserializer.class)
    private String created;
    private int depth;
    private String edited_at;

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

    @JsonSetter("edited")
    public void setEdited(JsonNode edited){
        if(!edited.isBoolean()){
            long timestamp = edited.asLong();
            Date date = new Date(timestamp * 1000);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.edited_at = formatter.format(date);
        }
    }


}
