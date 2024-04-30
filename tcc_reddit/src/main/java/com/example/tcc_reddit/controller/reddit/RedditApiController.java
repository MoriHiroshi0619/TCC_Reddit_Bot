package com.example.tcc_reddit.controller.reddit;

import com.example.tcc_reddit.DTOs.reddit.karma.KarmaDTO;
import com.example.tcc_reddit.DTOs.reddit.posts.RedditPostDTO;
import com.example.tcc_reddit.credentials.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/reddit-api")
public class RedditApiController extends BaseRedditController {
    private final RestTemplate restTemplate;
    private final HttpHeaders header;
    @Autowired
    public RedditApiController(Credentials credentials)
    {
        super(credentials);
        this.restTemplate = new RestTemplate();
        this.header = new HttpHeaders();
        this.header.set("User-Agent", getUserAgent());
        this.header.set("Authorization", getAccesstoken());
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

    @GetMapping("/new-posts-from/{subreddit}")
    public RedditPostDTO getNewPostsFromSubreddit (@PathVariable("subreddit") String subreddit) throws RedditApiException{
        //Os paremetros para essa requisição vão direto na url, não no body...
        String url = getEndpointPathWithSubreddit(RedditEndpoint.SUBREDDIT_NEW, subreddit + "?limit=1");

        HttpEntity<String> headerEntity = new HttpEntity<>(this.header);

        try{
            ResponseEntity<RedditPostDTO> response = restTemplate.exchange(url, HttpMethod.GET, headerEntity, RedditPostDTO.class);

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
}
