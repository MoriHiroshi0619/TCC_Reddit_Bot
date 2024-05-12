package com.example.tcc_reddit.DTOs.reddit.karma;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class KarmaDataDTO {
    private String sr;
    private int commentKarma;
    private int linkKarma;
}
