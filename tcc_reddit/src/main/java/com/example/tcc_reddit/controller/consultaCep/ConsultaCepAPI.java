package com.example.tcc_reddit.controller.consultaCep;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("consulta-cep")

public class ConsultaCepAPI {

    @GetMapping("{cep}")
    public CepResultadoDTO consultaCep(@PathVariable("cep") String cep){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CepResultadoDTO> resultado = restTemplate.getForEntity( String.format("https://viacep.com.br/ws/%s/json", cep), CepResultadoDTO.class);
        System.out.println(resultado);
        return resultado.getBody();
    }

    @GetMapping("teste")
    public String teste(){
        return "Hello World Desu Yone Kusotare !!";
    }

}
