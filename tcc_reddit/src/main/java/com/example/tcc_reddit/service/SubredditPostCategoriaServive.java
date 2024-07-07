package com.example.tcc_reddit.service;

import com.example.tcc_reddit.model.Categoria;
import com.example.tcc_reddit.model.SubRedditPost;
import com.example.tcc_reddit.model.SubredditPostCategoria;
import com.example.tcc_reddit.repository.SubRedditPostCategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class SubredditPostCategoriaServive {

    protected final SubRedditPostCategoriaRepository repository;

    public SubredditPostCategoriaServive (SubRedditPostCategoriaRepository repository){
        this.repository = repository;
    }

    public SubredditPostCategoria store(Categoria categoria, SubRedditPost subRedditPost, int peso){
        try{
            SubredditPostCategoria subredditPostCategoria = new SubredditPostCategoria();
            subredditPostCategoria.setPost(subRedditPost);
            subredditPostCategoria.setCategoria(categoria);
            subredditPostCategoria.setPeso(peso);
            return this.repository.save(subredditPostCategoria);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao relacinar categoria ao post: " + e.getMessage());
        }
    }
}
