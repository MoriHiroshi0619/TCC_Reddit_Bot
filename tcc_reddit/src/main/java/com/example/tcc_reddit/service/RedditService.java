package com.example.tcc_reddit.service;


import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditListingDTO;
import com.example.tcc_reddit.DTOs.reddit.postWatch.RedditPostDTO;
import com.example.tcc_reddit.DTOs.reddit.postWatch.RedditPostDataDTO;
import com.example.tcc_reddit.DTOs.reddit.subReddit.SubRedditDTO;
import com.example.tcc_reddit.controller.reddit.BaseReddit;
import com.example.tcc_reddit.controller.reddit.RedditApiException;
import com.example.tcc_reddit.credentials.Credentials;
import com.example.tcc_reddit.model.Municipio;
import com.example.tcc_reddit.model.SubReddit;
import com.example.tcc_reddit.model.SubRedditPost;
import com.example.tcc_reddit.repository.MunicipioRepository;
import com.example.tcc_reddit.repository.SubRedditPostRepository;
import com.example.tcc_reddit.repository.SubRedditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.Map;
import java.util.Optional;


@Service
public class RedditService extends BaseReddit {
    //todo criar metodos para o MunicicipioService
    //todo criar algoritmo de atribuição de municipio randomico
    //todo atribuir categoria
    //todo criar endpoints para verificar como está a situação do stream

    protected final SubRedditService subRedditService;

    protected final SubRedditPostService subRedditPostService;

    private static final ThreadLocal<String> requests_remaing = new ThreadLocal<>();
    private static final ThreadLocal<String> requests_used = new ThreadLocal<>();
    private static final ThreadLocal<String> requests_reset = new ThreadLocal<>();

    @Autowired
    public RedditService(SubRedditPostService subRedditPostService, Credentials credentials, SubRedditService subRedditService) {
        super(credentials);
        this.subRedditPostService = subRedditPostService;
        this.subRedditService = subRedditService;

    }

    public void streamSubredditPosts(String subredditName, int intervalo, String before, int limite, String sort) throws RedditApiException {
        Optional<SubReddit> subReddit = this.subRedditService.getBySubRedditName(subredditName);
        if(subReddit.isEmpty()){
            SubRedditDTO subRedditDTO = this.subRedditService.findSubReddit(subredditName);
            SubReddit newSubReddit = this.subRedditService.store(subRedditDTO.getData().getId(),
                                                                subRedditDTO.getData().getDisplay_name(),
                                                                subRedditDTO.getData().getTitle(),
                                                                subRedditDTO.getData().getDescription());
            this.subRedditService.save(newSubReddit);
            subReddit = Optional.of(newSubReddit);
        }
        while (true) {
            try {
                Map<String, Object> resultado = this.subRedditPostService.fetchPosts(subredditName, subReddit.get().getAfter(), before, limite, sort);
                RedditListingDTO posts = (RedditListingDTO) resultado.get("redditListinfDto");
                this.subRedditPostService.savePosts(posts);
                subReddit.get().setAfter(posts.getData().getAfter()); // Atualiza o 'after' para a próxima iteração
                this.subRedditService.save(subReddit.get());

                requests_remaing.set((String) resultado.get("requests_remaing"));
                requests_used.set((String) resultado.get("requests_used"));
                requests_reset.set((String) resultado.get("requests_reset"));

                Thread.sleep(intervalo * 1000L);
            }catch (RedditApiException | InterruptedException e){
                Thread.currentThread().interrupt();
                throw new RedditApiException("Streaming process interrupted: " + e.getMessage());
            }
        }
    }
}

