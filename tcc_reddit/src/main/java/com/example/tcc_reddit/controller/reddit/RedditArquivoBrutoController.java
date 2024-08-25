package com.example.tcc_reddit.controller.reddit;

import com.example.tcc_reddit.service.RedditArquivoBrutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reddit/arquivo-bruto")
public class RedditArquivoBrutoController {

    private Thread leituraArquivoBrutoThread;
    private volatile boolean leituraActive;
    private final RedditArquivoBrutoService service;

    @Autowired
    public RedditArquivoBrutoController(RedditArquivoBrutoService service) {
        this.service = service;
    }

    @PostMapping("/iniciar-leitura-de-arquivo-bruto")
    public ResponseEntity<String> iniciarLeituraDeArquivoBruto(@RequestBody Map<String, String> requestBody) {

        String caminhoArquivoZst = requestBody.get("caminhoArquivoZst");
        if (caminhoArquivoZst == null || caminhoArquivoZst.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O caminho do arquivo ZST é obrigatório");
        }

        try {
            if (leituraArquivoBrutoThread != null && leituraArquivoBrutoThread.isAlive()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A leitura já está ativa");
            }

            this.leituraActive = true;
            this.leituraArquivoBrutoThread = new Thread(() -> {
                try {
                    this.service.iniciarLeituraDeArquivoBruto(caminhoArquivoZst);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    this.leituraActive = false;
                }
            });
            this.leituraArquivoBrutoThread.start(); // Inicia a thread

            return ResponseEntity.ok("Iniciando leitura de arquivo bruto");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao iniciar leitura: " + e.getMessage());
        }
    }

    @GetMapping("/parar-leitura")
    public ResponseEntity<String> pararLeitura() {
        if (leituraArquivoBrutoThread != null && leituraArquivoBrutoThread.isAlive()) {
            leituraActive = false;
            leituraArquivoBrutoThread.interrupt(); // Interrompe a thread
            return ResponseEntity.ok("Leitura interrompida");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nenhuma leitura está ativa no momento");
        }
    }
}