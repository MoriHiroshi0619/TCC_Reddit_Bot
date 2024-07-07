package com.example.tcc_reddit.controller.reddit;

import com.example.tcc_reddit.DTOs.reddit.karma.KarmaDTO;
import com.example.tcc_reddit.credentials.Credentials;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class SubRedditKarmaController extends BaseReddit{

    public SubRedditKarmaController(Credentials credentials) {
        super(credentials);
    }

    @GetMapping("/my-karma")
    public KarmaDTO getMyKarma() throws RedditApiException{
        String url = getEndpoint(RedditEndpoint.KARMA);

        HttpHeaders header = new HttpHeaders();
        header.set("User-Agent", getUserAgent());
        header.set("Authorization", getAccesstoken());

        HttpEntity<String> entity = new HttpEntity<>(header);

        RestTemplate restTemplate = new RestTemplate();

        try{
            ResponseEntity<KarmaDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, KarmaDTO.class);

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
