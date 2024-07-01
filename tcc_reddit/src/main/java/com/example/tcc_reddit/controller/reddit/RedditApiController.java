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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/reddit-api")
public class RedditApiController extends BaseReddit {

    private final RedditService service;
    private final RestTemplate restTemplate;
    private final HttpHeaders header;

    private volatile boolean streamingActive = true;

    private Thread streamingThread;


    @Autowired
    public RedditApiController(Credentials credentials, RedditService service)
    {
        super(credentials);
        this.service = service;
        this.restTemplate = new RestTemplate();
        this.header = new HttpHeaders();
        this.header.set("User-Agent", getUserAgent());
        this.header.set("Authorization", getAccesstoken());
    }

    @GetMapping("/teste")
    public String teste(){
        return "Hello world [teste do docker porta 8090]";
    }

    @GetMapping("/accessToken")
    public String getMyAccessToken(){
        if(getAccesstoken() != null){
            return getAccesstoken();
        }else{
            return "Erro ao recuperar o accessToken.";
        }
    }

    @GetMapping("/my-karma")
    public KarmaDTO getMyKarma() throws RedditApiException{
        String url = getEndpoint(RedditEndpoint.KARMA);

        HttpEntity<String> headerEntity = new HttpEntity<>(this.header);

        try{
            ResponseEntity<KarmaDTO> response = this.restTemplate.exchange(url, HttpMethod.GET, headerEntity, KarmaDTO.class);

            if(response.getStatusCode() == HttpStatus.OK){
                return response.getBody();
            }else{
                return null;
            }
        }catch(HttpClientErrorException e){
            throw new RedditApiException("Erro do cliente: " + e.getMessage());
        }catch (HttpServerErrorException e){
            throw new RedditApiException("Erro do servidor: " + e.getMessage());
        }catch (Exception e){
            throw new RedditApiException("Error: " + e.getMessage());
        }
    }

    @GetMapping("/posts-from/{subreddit}")
    public RedditListingDTO getPostsFromSubreddit(
            @PathVariable("subreddit") String subreddit,
            @RequestParam(value = "after", required = false) String after,
            @RequestParam(value = "before", required = false) String before,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "sort", required = false, defaultValue = "new") String sort) throws RedditApiException {

        String url = getEndpointPathWithParam(RedditEndpoint.SUBREDDIT, subreddit);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sort", sort);

        if (after != null && !after.isEmpty()) {
            uriBuilder.queryParam("after", after);
        }
        if (before != null && !before.isEmpty()) {
            uriBuilder.queryParam("before", before);
        }
        if (limit != null) {
            uriBuilder.queryParam("limit", limit);
        }

        String finalUrl = uriBuilder.toUriString();

        System.out.println(finalUrl);

        HttpEntity<String> headerEntity = new HttpEntity<>(this.header);

        try {
            ResponseEntity<RedditListingDTO> response = this.restTemplate.exchange(finalUrl, HttpMethod.GET, headerEntity, RedditListingDTO.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (HttpClientErrorException e) {
            throw new RedditApiException("Erro do cliente: " + e.getMessage());
        } catch (HttpServerErrorException e) {
            throw new RedditApiException("Erro do servidor: " + e.getMessage());
        } catch (Exception e) {
            throw new RedditApiException("Error: " + e.getMessage());
        }
    }


    @GetMapping("/comments-from-a-post/{postID}")
    public List<RedditListingDTO> getCommentsFromAPost(@PathVariable("postID") String postID) throws RedditApiException{
        String url = getEndpointPathWithParam(RedditEndpoint.READ_POST_COMENTS, postID);
        HttpEntity<String> header = new HttpEntity<>(this.header);

        try{
            ResponseEntity<List<RedditListingDTO>> response = this.restTemplate.exchange(url, HttpMethod.GET, header, new ParameterizedTypeReference<List<RedditListingDTO>>() {
            });

            if(response.getStatusCode() == HttpStatus.OK){
                return response.getBody();
            }else{
                return null;
            }

        }catch(HttpClientErrorException e){
            throw new RedditApiException("Erro do cliente: " + e.getMessage());
        }catch (HttpServerErrorException e){
            throw new RedditApiException("Erro do servidor: " + e.getMessage());
        }catch (Exception e){
            throw new RedditApiException("Error: " + e.getMessage());
        }
    }

    @PostMapping("/submit-new-post")
    public RedditPostSubmitDTO newPostToSubreddit() throws RedditApiException{
        String url = getEndpoint(RedditEndpoint.NEW_POST);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("sr", "r/developerPeroNoMucho");
        body.add("title", "Segundo Teste");
        body.add("text", "Dessa vez a postagem é para testar o DTO");
        body.add("kind", "self");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, this.header);

        try{
            ResponseEntity<RedditPostSubmitDTO> response = this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, RedditPostSubmitDTO.class);

            if(response.getStatusCode() == HttpStatus.OK){
                return response.getBody();
            }else{
                return null;
            }
        }catch(HttpClientErrorException e){
            throw new RedditApiException("Erro do cliente: " + e.getMessage());
        }catch (HttpServerErrorException e){
            throw new RedditApiException("Erro do servidor: " + e.getMessage());
        }catch (Exception e){
            throw new RedditApiException("Error: " + e.getMessage());
        }
    }



    @GetMapping("/start-stream-subreddit/{subreddit}")
    public ResponseEntity<String> streamSubreddit(@PathVariable("subreddit") String subreddit) {
        try {
            //todo verificar se já tem ou não um tread ativo antes de ativar.
            this.streamingActive = true;
            this.streamingThread = new Thread(() -> {
                try {
                    while (streamingActive) {
                        this.service.streamSubredditPosts(subreddit);
                    }
                } catch (RedditApiException e) {
                    e.printStackTrace();
                }
            });
            this.streamingThread.start();
            return ResponseEntity.ok("Streaming started for subreddit: " + subreddit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error starting streaming: " + e.getMessage());
        }
    }

    @GetMapping("/stop-stream")
    public ResponseEntity<String> stopStreamSubreddit() {
        try {
            if (this.streamingThread != null && this.streamingThread.isAlive()) {
                streamingActive = false;
                this.streamingThread.interrupt();
                return ResponseEntity.ok("Streaming stopped");
            } else {
                return ResponseEntity.ok("No active streaming");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error stopping streaming: " + e.getMessage());
        }
    }
}

















