package com.example.tcc_reddit.service;

import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditChildDTO;
import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditListingDTO;
import com.example.tcc_reddit.DTOs.reddit.postSubmit.RedditPostSubmitDTO;
import com.example.tcc_reddit.DTOs.reddit.postWatch.RedditPostDTO;
import com.example.tcc_reddit.DTOs.reddit.postWatch.RedditPostDataDTO;
import com.example.tcc_reddit.controller.reddit.BaseReddit;
import com.example.tcc_reddit.controller.reddit.RedditApiException;
import com.example.tcc_reddit.credentials.Credentials;
import com.example.tcc_reddit.model.*;
import com.example.tcc_reddit.repository.SubRedditPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SubRedditPostService extends BaseReddit {

    protected final SubRedditPostRepository repository;

    @Autowired
    protected SubRedditService subRedditService;

    @Autowired
    protected CategoriaService categoriaService;

    @Autowired
    protected SubredditPostCategoriaServive subredditPostCategoriaServive;

    @Autowired
    protected MunicipioService municipioService;

    public SubRedditPostService (Credentials credentials, SubRedditPostRepository repository){
        super(credentials);
        this.repository = repository;
    }

    public Map<String, Object>fetchPosts(String subreddit, String after, String before, Integer limit, String sort) throws RedditApiException {
        String url = getEndpointPathWithParam(RedditEndpoint.FETCH_SUBREDDIT_POSTS, subreddit);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParam("sort", sort);

        if (after != null && !after.isEmpty()) { uriBuilder.queryParam("after", after); }
        if (before != null && !before.isEmpty()) { uriBuilder.queryParam("before", before); }
        if (limit != null) { uriBuilder.queryParam("limit", limit); }
        String finalUrl = uriBuilder.toUriString();

        HttpHeaders header = new HttpHeaders();
        header.set("User-Agent", getUserAgent());
        header.set("Authorization", getAccesstoken());

        HttpEntity<String> entity = new HttpEntity<>(header);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<RedditListingDTO> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, RedditListingDTO.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> resultado = new HashMap<>();
                resultado.put("redditListingDto", response.getBody());
                resultado.put("requests_remaing", response.getHeaders().get("x-ratelimit-remaining").get(0));
                resultado.put("requests_used", response.getHeaders().get("x-ratelimit-used").get(0));
                resultado.put("requests_reset", response.getHeaders().get("x-ratelimit-reset").get(0));
                return resultado;
            } else {
                throw new RedditApiException("Failed to fetch posts: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RedditApiException("Client error: " + e.getMessage());
        } catch (HttpServerErrorException e) {
            throw new RedditApiException("Server error: " + e.getMessage());
        } catch (Exception e) {
            throw new RedditApiException("Unexpected error: " + e.getMessage());
        }
    }

    public Map<String, Object> savePosts(RedditListingDTO posts, int pesoMinimo) {
        AtomicReference<RedditPostDataDTO> lastPostData = new AtomicReference<>();
        int totalSalvo = 0;
        Map<String, Object> resultado = new HashMap<>();
        List<RedditChildDTO> children = posts.getData().getChildren();
        for (int i = 0; i < children.size(); i++) {
            Object post = children.get(i);
            if (post instanceof RedditPostDTO) {
                try {
                    RedditPostDataDTO postData = ((RedditPostDTO) post).getData();
                    Optional<SubRedditPost> verificaSeExiste = this.repository.findFirstByPostId(postData.getId());
                    if (verificaSeExiste.isPresent()) {
                        continue;
                    }
                    lastPostData.set(postData);
                    SubRedditPost subRedditPost = new SubRedditPost();
                    subRedditPost.setPostId(postData.getId());
                    subRedditPost.setName(postData.getName());
                    subRedditPost.setSelftext(postData.getSelftext());
                    subRedditPost.setAuthor_id(postData.getAuthor_fullname());
                    subRedditPost.setAuthor(postData.getAuthor());
                    subRedditPost.setSaved(postData.isSaved());
                    subRedditPost.setSubreddit_name_prefixed(postData.getSubreddit_name_prefixed());
                    subRedditPost.setTitle(postData.getTitle());
                    subRedditPost.setUpvote_ratio(postData.getUpvote_ratio());
                    subRedditPost.setUps(postData.getUps());
                    subRedditPost.setDowns(postData.getDowns());
                    subRedditPost.setScore(postData.getScore());
                    subRedditPost.setCreated(postData.getCreated());
                    subRedditPost.setNum_comments(postData.getNum_comments());
                    subRedditPost.setUrl(postData.getUrl());
                    subRedditPost.setOver_18(postData.isOver_18());
                    subRedditPost.setCreated_utc(postData.getCreated_utc());
                    subRedditPost.setEdited_at(postData.getEdited_at());
                    Optional<SubReddit> subreddit = this.subRedditService.getByid(postData.getSubreddit_id());
                    subreddit.ifPresent(subRedditPost::setSubreddit_id);
                    Municipio municipio = this.municipioService.getRandomMunicipio();
                    subRedditPost.setMunicipio_id(municipio);
                    this.repository.save(subRedditPost);
                    totalSalvo+= 1;
                    List<Map<String, Object>> categorias = this.categoriaService.definirCategorias(postData.getTitle(), postData.getSelftext(), pesoMinimo);
                    categorias.forEach(categoria -> {
                        int categoriaId = (int) categoria.get("id_categoria");
                        int peso = (int) categoria.get("peso_postagem");
                        Optional<Categoria> categoriaEntity = this.categoriaService.getById(categoriaId);
                        categoriaEntity.ifPresent(categoriaGet -> this.subredditPostCategoriaServive.store(categoriaGet, subRedditPost, peso));
                    });
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao tentar salvar post: " + e.getMessage() + post);
                }
            }
        }
        RedditPostDataDTO lastPost = lastPostData.get();
        String lastPostId = null;
        if (lastPost != null) {
            System.out.println("Último Post - Title: " + lastPost.getTitle() + ", Created UTC: " + lastPost.getCreated_utc());
            lastPostId = lastPost.getId();
        }
        resultado.put("lastPostId", lastPostId);
        resultado.put("totalSalvo", totalSalvo);
        return resultado;
    }

    public List<RedditListingDTO> getCommentsFromAPost(String postID) throws RedditApiException{
        String url = getEndpointPathWithParam(RedditEndpoint.FETCH_POST_COMENTS, postID);

        HttpHeaders headerHttp = new HttpHeaders();
        headerHttp.set("User-Agent", getUserAgent());
        headerHttp.set("Authorization", getAccesstoken());

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> header = new HttpEntity<>(headerHttp);

        try{
            ResponseEntity<List<RedditListingDTO>> response = restTemplate.exchange(url, HttpMethod.GET, header, new ParameterizedTypeReference<List<RedditListingDTO>>() {
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

    public RedditPostSubmitDTO newPostToSubreddit()throws RedditApiException{
        String url = getEndpoint(RedditEndpoint.NEW_POST);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("sr", "r/developerPeroNoMucho");
        body.add("title", "Quase lah");
        body.add("text", "falta pouco");
        body.add("kind", "self");

        HttpHeaders header = new HttpHeaders();
        header.set("User-Agent", getUserAgent());
        header.set("Authorization", getAccesstoken());

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, header);

        try{
            ResponseEntity<RedditPostSubmitDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, RedditPostSubmitDTO.class);

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
