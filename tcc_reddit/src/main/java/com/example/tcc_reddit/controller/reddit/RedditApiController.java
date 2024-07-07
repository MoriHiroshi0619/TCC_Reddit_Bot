package com.example.tcc_reddit.controller.reddit;

import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditListingDTO;
import com.example.tcc_reddit.DTOs.reddit.karma.KarmaDTO;
import com.example.tcc_reddit.DTOs.reddit.postSubmit.RedditPostSubmitDTO;
import com.example.tcc_reddit.credentials.Credentials;
import com.example.tcc_reddit.service.RedditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reddit-api")
public class RedditApiController extends BaseReddit {

    private final RedditService service;
    private volatile boolean streamingActive;
    private Thread streamingThread;


    @Autowired
    public RedditApiController(Credentials credentials, RedditService service)
    {
        super(credentials);
        this.service = service;
    }

    @GetMapping("/accessToken")
    public String getMyAccessToken(){
        if(getAccesstoken() != null){
            return getAccesstoken();
        }else{
            return "Erro ao recuperar o accessToken.";
        }
    }

    @GetMapping("/start-stream-subreddit/{subreddit}")
    public ResponseEntity<String> streamSubreddit(@PathVariable("subreddit") String subreddit, @RequestBody Map<String, String> dados) {
        try {
            if(this.streamingActive){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O Stream já está ativo");
            }
            int intervalo = Integer.parseInt(dados.getOrDefault("intervalo", "10"));
            int limite = Integer.parseInt(dados.getOrDefault("limite", "50"));
            String sort = dados.getOrDefault("sort", "new");
            String before = dados.getOrDefault("before", null);

            this.streamingActive = true;
            this.streamingThread = new Thread(() -> {
                try {
                    while (streamingActive) {
                        this.service.streamSubredditPosts(subreddit, intervalo, before, limite, sort);
                    }
                } catch (RedditApiException e) {
                    e.printStackTrace();
                }
            });
            this.streamingThread.start();
            return ResponseEntity.ok("Streaming iniciado para o subreddit: " + subreddit);
        }catch (RedditApiException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao iniciar streaming: " + e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao iniciar streaming: " + e.getMessage());
        }
    }

    @GetMapping("/stop-stream")
    public ResponseEntity<String> stopStreamSubreddit() {
        try {
            if (this.streamingThread != null && this.streamingThread.isAlive()) {
                streamingActive = false;
                this.streamingThread.interrupt();
                return ResponseEntity.ok("Streaming encerrado");
            } else {
                return ResponseEntity.ok("Não tem stream ativo");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao parar streaming: " + e.getMessage());
        }
    }
}

















