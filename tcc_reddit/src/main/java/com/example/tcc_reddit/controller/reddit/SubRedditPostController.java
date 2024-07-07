package com.example.tcc_reddit.controller.reddit;

import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditListingDTO;
import com.example.tcc_reddit.DTOs.reddit.postSubmit.RedditPostSubmitDTO;
import com.example.tcc_reddit.service.SubRedditPostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SubRedditPostController {

    protected final SubRedditPostService service;

    public SubRedditPostController (SubRedditPostService service){
        this.service = service;
    }

    @GetMapping("/posts-from/{subreddit}")
    public RedditListingDTO getNewPostsFromSubreddit(
            @PathVariable("subreddit") String subreddit,
            @RequestParam(value = "after", required = false) String after,
            @RequestParam(value = "before", required = false) String before,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "sort", required = false, defaultValue = "new") String sort) throws RedditApiException {

        Map<String, Object> resultado = this.service.fetchPosts(subreddit, after, before, limit, sort);
        return (RedditListingDTO) resultado.get("redditListinfDto");
    }

    @PostMapping("/submit-new-post")
    public RedditPostSubmitDTO submitPost(){
        return this.service.newPostToSubreddit();
    }

    @GetMapping("/comments-from-a-post/{postID}")
    public List<RedditListingDTO> getCommentsFromAPost (@PathVariable("postID") String postID) throws RedditApiException{
        return this.service.getCommentsFromAPost(postID);
    }
}
