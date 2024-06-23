package com.example.tcc_reddit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subreddit_post_categoria")
public class Categoria {

    @Id @GeneratedValue
    private int id;

    private String nome;

    private String descricao;
}
