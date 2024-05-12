package com.example.tcc_reddit.controller.reddit;

import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditListingDTO;
import com.example.tcc_reddit.DTOs.reddit.karma.KarmaDTO;
import com.example.tcc_reddit.DTOs.reddit.postSubmit.RedditPostSubmitDTO;
import com.example.tcc_reddit.credentials.Credentials;
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

@RestController
@RequestMapping("/reddit-api")
public class RedditApiController extends BaseRedditController {
    //@todo vai ter que refatorar todo dos DTO.

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

    @GetMapping("/teste")
    public String teste(){
        return "Hello world";
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
    public RedditListingDTO getNewPostsFromSubreddit (@PathVariable("subreddit") String subreddit) throws RedditApiException{
        //Os paremetros para essa requisição vão direto na url, não no body...
        String url = getEndpointPathWithParam(RedditEndpoint.SUBREDDIT_NEW, subreddit);

        HttpEntity<String> headerEntity = new HttpEntity<>(this.header);

        try{
            ResponseEntity<RedditListingDTO> response = this.restTemplate.exchange(url, HttpMethod.GET, headerEntity, RedditListingDTO.class);

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

    //@todo quando for implementado o front-end e se der tempo eu implemento uma interface interativa para esse metodo
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
}

















