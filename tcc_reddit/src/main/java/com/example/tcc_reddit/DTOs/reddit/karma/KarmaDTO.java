package com.example.tcc_reddit.DTOs.reddit.karma;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KarmaDTO {
    private String kind;
    private List<KarmaDataDTO> data;
}
