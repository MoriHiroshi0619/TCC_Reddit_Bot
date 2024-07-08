package com.example.tcc_reddit.service;


import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditListingDTO;
import com.example.tcc_reddit.DTOs.reddit.subReddit.SubRedditDTO;
import com.example.tcc_reddit.controller.reddit.BaseReddit;
import com.example.tcc_reddit.controller.reddit.RedditApiException;
import com.example.tcc_reddit.credentials.Credentials;
import com.example.tcc_reddit.model.SubReddit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class RedditService extends BaseReddit {
    //todo criar endpoints para verificar como está a situação do stream
    //todo gerar alerta quando todos os posts de um subreddit forem lidos
    protected final SubRedditService subRedditService;

    protected final SubRedditPostService subRedditPostService;

    private static String requests_remaing;
    private static String requests_used;
    private static String requests_reset;

    @Autowired
    public RedditService(SubRedditPostService subRedditPostService, Credentials credentials, SubRedditService subRedditService) {
        super(credentials);
        this.subRedditPostService = subRedditPostService;
        this.subRedditService = subRedditService;

    }

    public void streamSubredditPosts(String subredditName, int intervalo, String before, int limite, String sort, int peso) throws RedditApiException {
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
        int contagem = 0;
        while (true) {
            try {
                Map<String, Object> resultado = this.subRedditPostService.fetchPosts(subredditName, subReddit.get().getAfter(), before, limite, sort);
                RedditListingDTO posts = (RedditListingDTO) resultado.get("redditListinfDto");
                this.subRedditPostService.savePosts(posts, peso);
                subReddit.get().setAfter(posts.getData().getAfter()); // Atualiza o 'after' para a próxima iteração
                this.subRedditService.save(subReddit.get());

                requests_remaing = (String) resultado.get("requests_remaing");
                requests_used = (String) resultado.get("requests_used");
                requests_reset = (String) resultado.get("requests_reset");

                contagem += posts.getData().getChildren().size();

                System.out.println("Total de posts lidos nessa seção -> " + contagem);
                System.out.println("API: requests remaing            -> " + requests_remaing);
                System.out.println("API: requests used               -> " + requests_used);
                System.out.println("API: requests to reset           -> " + requests_reset);
                System.out.println("-----------------------------------------");

                Thread.sleep(intervalo * 1000L);
            }catch (RedditApiException | InterruptedException e){
                Thread.currentThread().interrupt();
                throw new RedditApiException("Streaming process interrupted: " + e.getMessage());
            }
        }
    }
}

