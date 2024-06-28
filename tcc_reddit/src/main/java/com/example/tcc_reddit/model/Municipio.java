package com.example.tcc_reddit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "municipios")
public class Municipio {

    @Id
    private String geocodigo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String latitude;
}
