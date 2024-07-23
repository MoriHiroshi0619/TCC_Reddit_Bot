package com.example.tcc_reddit.service;


import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditListingDTO;
import com.example.tcc_reddit.DTOs.reddit.subReddit.SubRedditDTO;
import com.example.tcc_reddit.controller.reddit.BaseReddit;
import com.example.tcc_reddit.controller.reddit.RedditApiException;
import com.example.tcc_reddit.credentials.Credentials;
import com.example.tcc_reddit.model.SubReddit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class RedditService extends BaseReddit {
    //todo controle pra quando acabar todos os fetch interroper a thread
    //todo usar blocos try catch
    //todo implementar o uso de before pra recuperar os mais novos
    //todo verificar o erro Exception in thread "Thread-15" java.lang.RuntimeException: Erro ao recuperar o subreddit: Error creating bean with name 'spring.datasource-org.springframework.boot.autoconfigure.jdbc.DataSourceProperties': Could not bind properties to 'DataSourceProperties' : prefix=spring.datasource, ignoreInvalidFields=false, ignoreUnknownFields=true
    //todo melhorar a mensagem no console quando terminar o stream de um subreddit
    //todo tentat colocar o total lido por seção no console
    protected final SubRedditService subRedditService;

    protected final SubRedditPostService subRedditPostService;

    @Autowired
    public RedditService(SubRedditPostService subRedditPostService, Credentials credentials, SubRedditService subRedditService) {
        super(credentials);
        this.subRedditPostService = subRedditPostService;
        this.subRedditService = subRedditService;
    }

    public void streamSubreddits(List<String> subredditsName, int intervalo, int limite, String sort, int peso) throws RedditApiException{
        for (String subredditName : subredditsName) {
            this.fetchAndSaveSubReddit(subredditName);
        }
        List<SubReddit> subReddits = this.subRedditService.getAllSubReddits();
        for (SubReddit subReddit : subReddits) {
            this.streamSubredditPosts(subReddit, intervalo, limite, sort, peso);
        }
    }

    public void fetchAndSaveSubReddit(String subredditName) throws RedditApiException{
        SubRedditDTO subRedditDTO = this.subRedditService.findSubReddit(subredditName);
        Optional<SubReddit> subReddit = this.subRedditService.getByid(subRedditDTO.getData().getId());
        if(subReddit.isEmpty()){
            SubReddit newSubReddit = this.subRedditService.store(subRedditDTO.getData().getId(), subRedditDTO.getData().getDisplay_name(), subRedditDTO.getData().getTitle(), subRedditDTO.getData().getDescription());
            this.subRedditService.save(newSubReddit);
        }
    }

    private void streamSubredditPosts(SubReddit subReddit, int intervalo, int limite, String sort, int peso) throws RedditApiException {
        boolean aindaTemPost = true;
        while (aindaTemPost) {
            aindaTemPost = this.fetchAndSavePosts(subReddit, sort, limite, intervalo, peso);
        }
        System.out.println("Fim do stream para o subreddit   : " + subReddit.getSubRedditName());
    }

    private boolean fetchAndSavePosts(SubReddit subReddit, String sort, int limite, int intervalo, int peso)  throws RedditApiException{
        try {
            Map<String, Object> resultado = this.subRedditPostService.fetchPosts(subReddit.getSubRedditName(), subReddit.getAfter(), null, limite, sort);
            RedditListingDTO posts = (RedditListingDTO) resultado.get("redditListinfDto");
            String postId = this.subRedditPostService.savePosts(posts, peso);
            if (postId == null){
                return false;
            }
            subReddit.setAfter("t3_" + postId);
            this.subRedditService.save(subReddit);

            String requests_remaing = (String) resultado.get("requests_remaing");
            String requests_used = (String) resultado.get("requests_used");
            String requests_reset = (String) resultado.get("requests_reset");

            System.out.println("\n--------------------------------------------");
            System.out.println("API: requests remaing            -> " + requests_remaing);
            System.out.println("API: requests used               -> " + requests_used);
            System.out.println("API: requests to reset           -> " + requests_reset);
            System.out.println("Nome do Subreddit                -> " + subReddit.getSubRedditName());
            MemoryService.logMemoryUsage();

            Thread.sleep(intervalo * 1000L);
            return true;
        } catch (RedditApiException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RedditApiException("Streaming process interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}

