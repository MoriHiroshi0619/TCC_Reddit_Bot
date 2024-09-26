package com.example.tcc_reddit.controller.reddit;

import com.example.tcc_reddit.credentials.Credentials;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
abstract public class BaseReddit {
    private final String baseUrl;

    private final String userAgent;
    private static String accesstoken;

    public BaseReddit(Credentials credentials){
        this.baseUrl = "https://oauth.reddit.com/";
        this.userAgent = "TCC universitário";
        accesstoken = credentials.getAccessToken();
    }

    @Getter
    protected enum RedditEndpoint {
        KARMA("/api/v1/me/karma"),
        SUBREDDIT("r/{param}/about.json"),
        FETCH_SUBREDDIT_POSTS("/r/{param}/"),
        NEW_POST("/api/submit"),
        FETCH_POST_COMENTS("/comments/{param}");
        private final String path;

        RedditEndpoint(String path) {
            this.path = path;
        }

        public String getPathWithParam(String parametro) {
            return this.path.replace("{param}", parametro);
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

    protected String getEndpointPathWithParam(RedditEndpoint endpoint, String parametro) {
        return baseUrl + endpoint.getPathWithParam(parametro);
    }
}
