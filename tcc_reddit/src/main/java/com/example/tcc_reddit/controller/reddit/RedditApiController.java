package com.example.tcc_reddit.controller.reddit;

import com.example.tcc_reddit.credentials.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/reddit-api")
public class RedditApiController extends BaseRedditController {

    @Autowired
    public RedditApiController(Credentials credentials){
        super(credentials);
    }

    @GetMapping("/accessToken")
    public String getMyAccessToken(){
        if(getAccesstoken() != null){
            return getAccesstoken();
        }else{
            return "Erro ao recuperar o accessToken.";
        }
    }

    @GetMapping("/meu-karma")
    public String getMyKarma(){
        RestTemplate restTemplate = new RestTemplate();
        String url = getEndpoint(RedditEndpoint.KARMA);

        HttpHeaders header = new HttpHeaders();
        header.set("User-Agent", getUserAgent());
        header.set("Authorization", getAccesstoken());
        HttpEntity<String> headerEntity = new HttpEntity<>(header);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, headerEntity, String.class);

        if(response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        } else {
            return "Erro ao recuperar o Karma do usuário.";
        }
    }

}
