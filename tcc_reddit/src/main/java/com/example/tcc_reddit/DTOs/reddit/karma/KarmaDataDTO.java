package com.example.tcc_reddit.DTOs.reddit.karma;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KarmaDataDTO {
    private String sr;
    private int commentKarma;
    private int linkKarma;
}
