package com.example.tcc_reddit.DTOs.reddit.karma;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class KarmaDTO {
    private String kind;
    private List<KarmaDataDTO> data;
}
