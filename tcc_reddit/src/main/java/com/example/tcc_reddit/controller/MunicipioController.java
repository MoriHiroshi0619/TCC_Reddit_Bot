package com.example.tcc_reddit.controller;

import com.example.tcc_reddit.service.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/municipios")
public class MunicipioController {

    @Autowired
    private MunicipioService municipioService;

    //para ler a partir de um input file de uma pagina HTML
    /*@PostMapping("/importar-from-arquivo")
    public ResponseEntity<String> importarMunicipiosFromArquivo(@RequestParam("file") MultipartFile file) {
        try {
            municipioService.importarMunicipiosDoExcel(file.getInputStream().toString());
            return ResponseEntity.status(HttpStatus.OK).body("Municípios importados com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao importar municípios: " + e.getMessage());
        }
    }*/

    @PostMapping("/importar")
    public ResponseEntity<String> importarMunicipios() {
        try {
            municipioService.importarMunicipiosDoExcel();
            return ResponseEntity.status(HttpStatus.OK).body("Municípios importados com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao importar municípios: " + e.getMessage());
        }
    }
}

