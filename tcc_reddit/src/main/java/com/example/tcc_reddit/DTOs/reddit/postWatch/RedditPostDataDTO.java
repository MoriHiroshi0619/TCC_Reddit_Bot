package com.example.tcc_reddit.DTOs.reddit.postWatch;

import com.example.tcc_reddit.DTOs.CustomUnixTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RedditPostDataDTO {
    private String subreddit;
    private String selftext;
    private String author_fullname;
    private String author;
    private boolean saved;
    private boolean approved;
    @JsonDeserialize( using = CustomUnixTimeDeserializer.class)
    private String approved_at_utc;
    private String approved_by;
    private String subreddit_name_prefixed;
    private String title;
    private float upvote_ratio;
    private int ups;
    private int score;
    @JsonDeserialize( using = CustomUnixTimeDeserializer.class)
    private String created;
    private String id;
    private int num_comments;
    private String url;
    private boolean over_18;
    @JsonDeserialize( using = CustomUnixTimeDeserializer.class)
    private String created_utc;
    private boolean was_edited;
    private float edited_at;

    @JsonSetter("edited")
    public void setEdited(JsonNode edited){
        if(!edited.isBoolean()){
            this.was_edited = true;
            this.edited_at = (float) edited.asDouble();
        }else{
            this.was_edited = false;
        }
    }
}
