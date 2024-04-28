package com.example.tcc_reddit.DTOs.reddit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenDTO {

    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("token_type")
    private String token_type;

    @JsonProperty("expires_in")
    private String expires_in;

    @JsonProperty("scope")
    private String scope;

}
