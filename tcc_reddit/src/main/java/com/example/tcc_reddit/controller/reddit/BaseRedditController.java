package com.example.tcc_reddit.controller.reddit;

import com.example.tcc_reddit.credentials.Credentials;
import org.springframework.beans.factory.annotation.Autowired;

abstract public class BaseRedditController {
    protected final String baseUrl;
    protected static String accesstoken;

    public BaseRedditController(Credentials credentials){
        this.baseUrl = "https://oauth.reddit.com/";
        accesstoken = credentials.getAccessToken();
    }

    protected String getAccesstoken(){
        return accesstoken;
    }

}
