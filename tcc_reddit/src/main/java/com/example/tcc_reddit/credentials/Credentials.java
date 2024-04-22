package com.example.tcc_reddit.credentials;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Base64;

@Component
public class Credentials {

    @Value("${reddit.client-id}")
    private String cliend_id;

    @Value("${reddit.secret-id}")
    private String secret_id;

    @Value("${reddit.username}")
    private String username;

    @Value("${reddit.password}")
    private String password;

    private static AccessTokenResponce accessToken;

    public String getAccessToken(){
        if(this.accessTokenAindaEhValido()){
            return accessToken.getAccess_token();
        }else{
            this.ObterAccessToken();
            return accessToken.getToken_type();
        }
    }

    private boolean accessTokenAindaEhValido(){
        if(accessToken == null){
            return false;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiracaoDoAccessToken = currentTime.plusSeconds(Long.parseLong(accessToken.getExpires_in()));
        return currentTime.isBefore(expiracaoDoAccessToken);
    }

    private void ObterAccessToken(){
        String clientIdESecretId = this.cliend_id + ":" + this.secret_id;
        String autorizacaoEnconded = Base64.getEncoder().encodeToString(clientIdESecretId.getBytes());

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.add("Authorization", "Basic " + autorizacaoEnconded);
        header.set("User-Agent", "tccBot");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", this.username);
        body.add("password", this.password);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, header);

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.reddit.com/api/v1/access_token";

        ResponseEntity<AccessTokenResponce> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, AccessTokenResponce.class);
        accessToken = response.getBody();
    }

    /*private void ObterAccessToken(){
        RestTemplate restTemplate = new RestTemplate();sao
        String url_request = "https://www.reddit.com/api/v1/access_token";
        String grant_type = "password";
        String requestBody =
                "grant_type=" + grant_type
                + "&client_id=" + this.cliend_id
                + "&password" + this.secret_id;
        accessToken = restTemplate.postForObject(url_request, requestBody, AccessTokenResponce.class);
    }*/



}
