package com.example.tcc_reddit.DTOs.reddit.posts;

import com.example.tcc_reddit.DTOs.JsonMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditPostDataDetailsDTO {
    private Long approved_at_utc;
    private String subreddit;
    private String selftext;
    private String author_fullname;
    private Boolean saved;
    private String mod_reason_title;
    private Integer gilded;
    private Boolean clicked;
    private String title;
    private List<Object> link_flair_richtext;
    private String subreddit_name_prefixed;
    private Boolean hidden;
    private Integer pwls;
    private String link_flair_css_class;
    private Integer downs;
    private Integer thumbnail_height;
    private String top_awarded_type;
    private Boolean hide_score;
    private String name;
    private Boolean quarantine;
    private String link_flair_text_color;
    private Double upvote_ratio;
    private String author_flair_background_color;
    private String subreddit_type;
    private Integer ups;
    private Integer total_awards_received;
    private Map<String, Object> media_embed;
    private Integer thumbnail_width;
    private String author_flair_template_id;
    private Boolean is_original_content;
    private List<Object> user_reports;
    private Object secure_media;
    private Boolean is_reddit_media_domain;
    private Boolean is_meta;
    private Object category;
    private Map<String, Object> secure_media_embed;
    private Object link_flair_text;
    private Boolean can_mod_post;
    private Integer score;
    private String approved_by;
    private Boolean is_created_from_ads_ui;
    private Boolean author_premium;
    private String thumbnail;
    private Boolean edited;
    private String author_flair_css_class;
    private List<Object> author_flair_richtext;
    private Map<String, Integer> gildings;
    private Object content_categories;
    private Boolean is_self;
    private String mod_note;
    private Double created;
    private String link_flair_type;
    private Object wls;
    private Object removed_by_category;
    private Object banned_by;
    private String author_flair_type;
    private String domain;
    private Boolean allow_live_comments;
    private String selftext_html;
    private Object likes;
    private String suggested_sort;
    private Object banned_at_utc;
    private Object view_count;
    private Boolean archived;
    private Boolean no_follow;
    private Boolean is_crosspostable;
    private Boolean pinned;
    private Boolean over_18;
    private List<Object> all_awardings;
    private List<Object> awarders;
    private Boolean media_only;
    private String link_flair_template_id;
    private Boolean can_gild;
    private Boolean spoiler;
    private Boolean locked;
    private String author_flair_text;
    private List<Object> treatment_tags;
    private Boolean visited;
    private Object removed_by;
    private Object num_reports;
    private Object distinguished;
    private String subreddit_id;
    private Boolean author_is_blocked;
    private Object mod_reason_by;
    private Object removal_reason;
    private String link_flair_background_color;
    private String id;
    private Boolean is_robot_indexable;
    private Object report_reasons;
    private String author;
    private Object discussion_type;
    private Integer num_comments;
    private Boolean send_replies;
    private String whitelist_status;
    private Boolean contest_mode;
    private List<Object> mod_reports;
    private Boolean author_patreon_flair;
    private String author_flair_text_color;
    private String permalink;
    private String parent_whitelist_status;
    private Boolean stickied;
    private String url;
    private Integer subreddit_subscribers;
    private Double created_utc;
    private Integer num_crossposts;
    private Object media;
    private Boolean is_video;

    public static RedditPostDataDetailsDTO fromJson(String json) throws JsonProcessingException {
        return JsonMapper.getObjectMapper().readValue(json, RedditPostDataDetailsDTO.class);
    }
}
