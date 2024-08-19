package com.example.tcc_reddit.service;

import com.example.tcc_reddit.model.Categoria;
import com.example.tcc_reddit.model.SubRedditPostArquivoBruto;
import com.example.tcc_reddit.model.SubredditpostCategoriaArquivoBruto;
import com.example.tcc_reddit.repository.SubRedditPostCategoriaArquivoBrutoRepository;
import org.springframework.stereotype.Service;

@Service
public class SubredditPostCategoriaArquivoBrutoService {

    protected final SubRedditPostCategoriaArquivoBrutoRepository repository;

    public SubredditPostCategoriaArquivoBrutoService(SubRedditPostCategoriaArquivoBrutoRepository repository){
        this.repository = repository;
    }

    public SubredditpostCategoriaArquivoBruto store(Categoria categoria, SubRedditPostArquivoBruto subRedditPostArquivoBruto, int peso){
        try {
            SubredditpostCategoriaArquivoBruto subredditPostCategoriaArquivoBruto = new SubredditpostCategoriaArquivoBruto();
            subredditPostCategoriaArquivoBruto.setPost(subRedditPostArquivoBruto);
            subredditPostCategoriaArquivoBruto.setCategoria(categoria);
            subredditPostCategoriaArquivoBruto.setPeso(peso);
            return this.repository.save(subredditPostCategoriaArquivoBruto);
        }catch (Exception e){
            throw new RuntimeException("Erro ao relacinar categoria ao post: " + e.getMessage());
        }
    }
}
