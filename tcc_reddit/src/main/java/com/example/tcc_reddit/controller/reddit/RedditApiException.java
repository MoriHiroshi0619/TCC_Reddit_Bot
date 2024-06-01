package com.example.tcc_reddit.controller.reddit;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RedditApiException extends RuntimeException{

    public RedditApiException() {
        super();
    }
    public RedditApiException(String message) {
        super(message);
    }

    public RedditApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
