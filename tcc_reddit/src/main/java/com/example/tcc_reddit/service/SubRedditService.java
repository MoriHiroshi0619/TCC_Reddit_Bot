package com.example.tcc_reddit.service;

import com.example.tcc_reddit.DTOs.reddit.subReddit.SubRedditDTO;
import com.example.tcc_reddit.controller.reddit.BaseReddit;
import com.example.tcc_reddit.controller.reddit.RedditApiException;
import com.example.tcc_reddit.credentials.Credentials;
import com.example.tcc_reddit.model.SubReddit;
import com.example.tcc_reddit.repository.SubRedditRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class SubRedditService extends BaseReddit {

    protected final SubRedditRepository repository;

    public SubRedditService(SubRedditRepository repository, Credentials credentials){
        super(credentials);
        this.repository = repository;
    }

    public Optional<SubReddit> getByid(String id){
        try{
            Optional<SubReddit> subReddit = this.repository.findFirstBySubRedditId(id);
            if(subReddit.isPresent()){
                subReddit.get();
                return subReddit;
            }
            return Optional.empty();
        }catch (Exception e){
            throw new RuntimeException("Erro ao recuperar o subreddit: " + e.getMessage());
        }
    }

    public Optional<SubReddit> getBySubRedditName(String nome){
        try{
            Optional<SubReddit> subReddit = this.repository.findFirstBySubRedditName(nome);
            if(subReddit.isPresent()){
                subReddit.get();
                return subReddit;
            }
            return Optional.empty();
        }catch (Exception e){
            throw new RuntimeException("Erro ao recuperar o subreddit: " + e.getMessage());
        }
    }

    public List<SubReddit> getAllSubReddits(){
        try {
            return this.repository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Erro ao recuperar os subreddits: " + e.getMessage());
        }
    }

    @Transactional
    public SubReddit store(String subRedditId, String nome, String titulo, String descricao){
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do subReddit não pode ser vazio.");
        }
        try{
            SubReddit subReddit = new SubReddit(subRedditId, nome, titulo, descricao);
            return this.repository.save(subReddit);
        }catch (Exception e) {
            throw new RuntimeException("Erro ao criar a categoria: " + e.getMessage());
        }
    }

    public void save(SubReddit subReddit){
        try {
            this.repository.save(subReddit);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar a categoria: " + e.getMessage());
        }
    }

    public SubRedditDTO findSubReddit(String subRedditName){
        String url = getEndpointPathWithParam(RedditEndpoint.SUBREDDIT, subRedditName);

        HttpHeaders header = new HttpHeaders();
        header.set("User-Agent", getUserAgent());
        header.set("Authorization", getAccesstoken());

        HttpEntity<String> entity = new HttpEntity<>(header);

        RestTemplate restTemplate = new RestTemplate();

        try{
            ResponseEntity<SubRedditDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, SubRedditDTO.class);

            if(response.getStatusCode() == HttpStatus.OK){
                return response.getBody();
            }else{
                return null;
            }
        }catch (Exception e){
            throw new RedditApiException("Error: " + e.getMessage());
        }
    }
}


















