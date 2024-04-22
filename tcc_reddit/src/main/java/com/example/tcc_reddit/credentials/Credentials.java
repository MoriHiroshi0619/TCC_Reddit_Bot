package com.example.tcc_reddit.credentials;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
public class Credentials {

    @Value("${reddit.client-id}")
    private String cliend_id;

    @Value("${reddit.secret-id}")
    private String secret_id;

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
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiracaoDoAccessToken = currentTime.plusSeconds(Long.parseLong(accessToken.getExpires_in()));
        return currentTime.isBefore(expiracaoDoAccessToken);
    }

    private void ObterAccessToken(){
        RestTemplate restTemplate = new RestTemplate();
        String url_request = "https://www.reddit.com/api/v1/access_token";
        String grant_type = "password";
        String requestBody =
                "grant_type=" + grant_type
                + "&client_id=" + this.cliend_id
                + "&client_secret" + this.secret_id;
        accessToken = restTemplate.postForObject(url_request, requestBody, AccessTokenResponce.class);
    }

}
