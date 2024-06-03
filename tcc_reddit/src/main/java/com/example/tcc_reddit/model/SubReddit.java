package com.example.tcc_reddit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "subreddit_subreddit")
public class SubReddit{
    @Id
    private String id;
    private String subredditName;

    public SubReddit() {

    }
    public SubReddit(String id, String subredditName) {
        this.id = id;
        this.subredditName = subredditName;
    }

}
