package com.example.tcc_reddit.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import com.github.luben.zstd.*;

import java.io.*;

@Service
public class RedditArquivoBrutoService {

    protected final SubRedditPostArquivoBrutoService subRedditPostArquivoBrutoService;

    public RedditArquivoBrutoService(SubRedditPostArquivoBrutoService subRedditPostArquivoBrutoService) {
        this.subRedditPostArquivoBrutoService = subRedditPostArquivoBrutoService;
    }

    public void iniciarLeituraDeArquivoBruto(String caminhoArquivoZst) throws IOException {
        int pesoMinimo = 9;
        System.out.println("Iniciando leitura de arquivo bruto: " + caminhoArquivoZst);
        // Cria o ObjectMapper para processar JSON
        ObjectMapper objectMapper = new ObjectMapper();

        try (
                FileInputStream fis = new FileInputStream(caminhoArquivoZst);
                ZstdInputStream zstdInputStream = new ZstdInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(zstdInputStream))
        ) {
            String linha;
            int totalSalvo = 0;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.isEmpty()) {
                    continue;
                }

                try {
                    // Converte a linha para um objeto JsonNode
                    JsonNode jsonNode = objectMapper.readTree(linha);
                    this.subRedditPostArquivoBrutoService.savePost(jsonNode, pesoMinimo);
                    totalSalvo++;
                    if(totalSalvo % 100 == 0){
                        System.out.println("Total de posts salvos até o momento: " + totalSalvo);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao processar JSON: " + e.getMessage());
                }
            }
            System.out.println("Total de posts salvos: " + totalSalvo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}