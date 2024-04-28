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
        KARMA("/api/v1/me/karma");
        private final String value;

        RedditEndpoint(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    protected String getAccesstoken(){
        return "Bearer " + accesstoken;
    }

    protected String getUserAgent(){
        return this.userAgent;
    }

    protected String getEndpoint(RedditEndpoint endpoint) {
        return baseUrl + endpoint.getValue();
    }

}
