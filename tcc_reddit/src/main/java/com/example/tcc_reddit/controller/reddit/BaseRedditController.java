package com.example.tcc_reddit.controller.reddit;

import com.example.tcc_reddit.credentials.Credentials;

abstract public class BaseRedditController {
    private final String baseUrl;

    private final String userAgent;
    private static String accesstoken;

    public BaseRedditController(Credentials credentials){
        this.baseUrl = "https://oauth.reddit.com/";
        this.userAgent = "tccBot";
        accesstoken = credentials.getAccessToken();
    }

    enum RedditEndpoint {
        KARMA("/api/v1/me/karma"),
        SUBREDDIT_NEW("/r/{subreddit}/new");
        private final String path;

        RedditEndpoint(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public String getPathWithSubreddit(String subreddit) {
            return this.path.replace("{subreddit}", subreddit);
        }

    }

    protected String getAccesstoken()
    {
        return "Bearer " + accesstoken;
    }

    protected String getUserAgent()
    {
        return this.userAgent;

    }

    protected String getEndpoint(RedditEndpoint endpoint)
    {
        return baseUrl + endpoint.getPath();
    }

    protected String getEndpointPathWithSubreddit(RedditEndpoint endpoint, String subreddit) {
        return baseUrl + endpoint.getPathWithSubreddit(subreddit);
    }



}
