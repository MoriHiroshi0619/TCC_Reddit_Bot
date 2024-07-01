package com.example.tcc_reddit.service;


import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditListingDTO;
import com.example.tcc_reddit.DTOs.reddit.postWatch.RedditPostDTO;
import com.example.tcc_reddit.DTOs.reddit.postWatch.RedditPostDataDTO;
import com.example.tcc_reddit.controller.reddit.BaseReddit;
import com.example.tcc_reddit.controller.reddit.RedditApiException;
import com.example.tcc_reddit.credentials.Credentials;
import com.example.tcc_reddit.model.Municipio;
import com.example.tcc_reddit.model.SubReddit;
import com.example.tcc_reddit.model.SubRedditPost;
import com.example.tcc_reddit.repository.MunicipioRepository;
import com.example.tcc_reddit.repository.SubRedditPostRepository;
import com.example.tcc_reddit.repository.SubRedditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.Optional;


@Service
public class RedditService extends BaseReddit {
    //todo pesquisar melhor como parar a thread
    //todo criar metodos para o MunicicipioService
    //todo criar metodos para o SubRedditService
    //todo criar algoritmo de atribuição de municipio randomico
    //todo atribuir categoria
    //todo criar endpoints para verificar como está a situação do stream
    protected final SubRedditPostRepository repository;

    @Autowired
    protected MunicipioRepository municipioRepository;

    @Autowired
    protected SubRedditRepository subRedditRepository;

    private static final ThreadLocal<String> requests_remaing = new ThreadLocal<>();
    private static final ThreadLocal<String> requests_used = new ThreadLocal<>();
    private static final ThreadLocal<String> requests_reset = new ThreadLocal<>();

    @Autowired
    public RedditService(SubRedditPostRepository repository, Credentials credentials) {
        super(credentials);
        this.repository = repository;
    }

    public RedditListingDTO fetchPosts(String subreddit, String after, String before, Integer limit, String sort) throws RedditApiException {
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

        HttpHeaders header = new HttpHeaders();
        header.set("User-Agent", getUserAgent());
        header.set("Authorization", getAccesstoken());

        HttpEntity<String> entity = new HttpEntity<>(header);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<RedditListingDTO> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, RedditListingDTO.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                requests_remaing.set(response.getHeaders().get("x-ratelimit-remaining").get(0));
                requests_used.set(response.getHeaders().get("x-ratelimit-used").get(0));
                requests_reset.set(response.getHeaders().get("x-ratelimit-reset").get(0));
                return response.getBody();
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

    public void streamSubredditPosts(String subreddit) throws RedditApiException {
        String after = null;
        while (true) {
            try {
                RedditListingDTO posts = fetchPosts(subreddit, after, null, 2, "new");
                this.savePosts(posts);
                after = posts.getData().getAfter(); // Atualiza o 'after' para a próxima iteração
                Thread.sleep(60 * 500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RedditApiException("Streaming process interrupted: " + e.getMessage());
            }
        }
    }

    private void savePosts(RedditListingDTO posts) {
        posts.getData().getChildren().forEach(post -> {
            if (post instanceof RedditPostDTO) {
                RedditPostDataDTO postData = ((RedditPostDTO) post).getData();

                SubRedditPost subRedditPost = new SubRedditPost();
                subRedditPost.setId(postData.getId());
                subRedditPost.setName(postData.getName());
                subRedditPost.setSelftext(postData.getSelftext());
                subRedditPost.setAuthor_id(postData.getAuthor_fullname());
                subRedditPost.setAuthor(postData.getAuthor());
                subRedditPost.setSaved(postData.isSaved());
                subRedditPost.setApproved(postData.isApproved());
                subRedditPost.setApproved_at_utc(postData.getApproved_at_utc());
                subRedditPost.setApproved_by(postData.getApproved_by());
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

                // Recupera ou cria o SubReddit
                SubReddit subreddit = this.recuperarOuCriarSubReddit(postData.getSubreddit_id(), postData.getSubreddit());
                subRedditPost.setSubreddit_id(subreddit);

                // Recupera ou cria o Município
                Optional<Municipio> municipio = this.recuperarOuCriarMunicipio(postData.getSubreddit_id());
                municipio.ifPresent(subRedditPost::setMunicipio_id); // Seta o municipio apenas se presente

                repository.save(subRedditPost);
            }
        });
    }

    private Optional<Municipio> recuperarOuCriarMunicipio(String municipioId) {
        return municipioRepository.findById("11011468");
    }

    private SubReddit recuperarOuCriarSubReddit(String subredditId, String subreddit) {
        Optional<SubReddit> subRedditOptional = subRedditRepository.findById(subredditId);
        if (subRedditOptional.isPresent()) {
            return subRedditOptional.get();
        } else {
            SubReddit newSubReddit = new SubReddit(subredditId, subreddit);
            subRedditRepository.save(newSubReddit);
            return newSubReddit;
        }
    }
}

