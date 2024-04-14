package com.example.tcc_reddit.controller.consultaCep;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CepResultadoDTO {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
}