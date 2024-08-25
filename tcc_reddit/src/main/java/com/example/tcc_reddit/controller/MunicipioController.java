package com.example.tcc_reddit.controller;

import com.example.tcc_reddit.service.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/municipios")
public class MunicipioController {

    @Autowired
    private MunicipioService municipioService;

    @PostMapping("/importar")
    public ResponseEntity<String> importarMunicipios(@RequestBody Map<String, String> requestBody) {
        String caminhoArquivo = requestBody.get("caminhoArquivo");
        if (caminhoArquivo == null || caminhoArquivo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O caminho do arquivo é obrigatório");
        }
        try {
            municipioService.importarMunicipiosDoExcel(caminhoArquivo);
            return ResponseEntity.status(HttpStatus.OK).body("Municípios importados com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao importar municípios: " + e.getMessage());
        }
    }
}

