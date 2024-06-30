package com.example.tcc_reddit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categoria")
public class Categoria {

    //auto incrementa o id
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = true)
    private String descricao;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubredditPostCategoria> posts = new HashSet<>();

    public Categoria(){};
    public Categoria(String nome){
        this.nome = nome;
    }
    public Categoria(String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
    }
}
