package com.example.tcc_reddit.controller.reddit;

import com.example.tcc_reddit.DTOs.reddit.subReddit.SubRedditDTO;
import com.example.tcc_reddit.service.SubRedditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubRedditController {
    private final SubRedditService service;

    @Autowired
    public SubRedditController (SubRedditService service){
        this.service = service;
    }

    @GetMapping("subreddit/{subRedditNome}")
    public SubRedditDTO getSubReddit(@PathVariable("subRedditNome") String nome){
        return this.service.findSubReddit(nome);
    }
}
