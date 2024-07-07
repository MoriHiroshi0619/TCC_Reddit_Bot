package com.example.tcc_reddit.service;

import com.example.tcc_reddit.model.Categoria;
import com.example.tcc_reddit.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoriaService {
    protected final CategoriaRepository repository;
    protected List<String> categoriasPredefinidas;

    @Autowired
    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }


    public void categoriasPredefinidasRefresh() {
        this.categoriasPredefinidas = new ArrayList<>();
        this.repository.findAll().forEach(categoria -> this.categoriasPredefinidas.add(categoria.getNome()));
    }

    public Optional<Categoria> getById(int id){
        try{
            Optional<Categoria> categoria = this.repository.findById(id);
            if(categoria.isPresent()){
                categoria.get();
                return categoria;
            }
            return Optional.empty();
        }catch (Exception e){
            throw new RuntimeException("Erro ao recuperar a categoria: " + e.getMessage());
        }
    }

    @Transactional
    public Categoria store(String nome, String descricao) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria não pode ser vazio.");
        }

        try {
            if (this.repository.findFirstByNome(nome.trim()).isPresent()) {
                throw new IllegalArgumentException("Uma categoria com esse nome já existe.");
            }

            Categoria categoria = new Categoria();
            categoria.setNome(nome.trim());
            if (descricao != null && !descricao.trim().isEmpty()) {
                categoria.setDescricao(descricao.trim());
            }

            return this.repository.save(categoria);

        }catch (IllegalArgumentException e){
            throw new RuntimeException("Argumentos inválidos: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar a categoria: " + e.getMessage());
        }
    }


    public Optional<Categoria> delete(int id){
        try{
            Optional<Categoria> categoria = this.repository.findById(id);
            if(categoria.isPresent()){
                categoria.get();
                this.repository.deleteById(id);
                return categoria;
            }else{
                throw new IllegalArgumentException("Não foi possivel encontrar categoria com esse id");
            }
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Argumentos inválidos:" + e.getMessage());
        }catch (Exception e){
            throw new RuntimeException("Erro ao criar a categoria: " + e.getMessage());
        }
    }

    @Transactional
    public List<Map<String, Object>> definirCategorias(String titulo, String corpo) {
        Map<String, Integer> categoriaPesos = new HashMap<>();

        this.categoriasPredefinidasRefresh();

         //peso minimo pra atribuir uma postagem a uma categoria
        int pesoNecessario = 3;

        for (String categoria : this.categoriasPredefinidas) {
            int peso = 0;

            // Verifica se a categoria está no título (adiciona 9)
            if (titulo.toLowerCase().contains(categoria.toLowerCase())) {
                peso += 9;
            }

            // Verifica as ocorrências da categoria no corpo (adiciona 3 para cada ocorrência)
            int occurrences = getOcorrenciaDeCategoria(corpo.trim().toLowerCase(), categoria.toLowerCase());
            peso += occurrences * 3;

            if (peso >= pesoNecessario) {
                categoriaPesos.put(categoria, peso);
            }
        }

        List<Map<String, Object>> resultado = new ArrayList<>();
        if (!categoriaPesos.isEmpty()) {
            List<Categoria> categorias = this.repository.findByNomeIn(new ArrayList<>(categoriaPesos.keySet()));
            for (Categoria categoria : categorias) {
                Map<String, Object> categoriaInfo = new HashMap<>();
                categoriaInfo.put("id_categoria", categoria.getId());
                categoriaInfo.put("nome_categoria", categoria.getNome());
                categoriaInfo.put("peso_postagem", categoriaPesos.get(categoria.getNome()));
                resultado.add(categoriaInfo);
            }
        }

        return resultado;
    }

    private int getOcorrenciaDeCategoria(String texto, String categoria) {
        int index = 0;
        int count = 0;
        while ((index = texto.toLowerCase().indexOf(categoria, index)) != -1) {
            count++;
            index += categoria.length();
        }
        return count;
    }
}
