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
        ResponseEntity<CepResultadoDTO> resultado = restTemplate.getForEntity("viacep.com.br/ws/01001000/json/", CepResultadoDTO.class);

        return resultado.getBody();
    }
}
