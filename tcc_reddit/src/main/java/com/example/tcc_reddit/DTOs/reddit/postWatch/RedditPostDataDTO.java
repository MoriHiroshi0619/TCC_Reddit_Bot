package com.example.tcc_reddit.DTOs.reddit.postWatch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RedditPostDataDTO {
    //@todo por algum motivo dependendo do que a API devolve esse formato pode dar errado... [EDITED]
    private String subreddit;
    private String selftext;
    private String author_fullname;
    private String author;
    private boolean saved;
    private boolean approved;
    private String approved_at_utc;
    private String approved_by;
    private String subreddit_name_prefixed;
    private String title;
    private float upvote_ratio;
    private int ups;
    private int score;
    private String created;
    private String id;
    private int num_comments;
    private String url;
    private boolean over_18;
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
