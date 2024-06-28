package com.example.tcc_reddit.controller;

import com.example.tcc_reddit.model.Categoria;
import com.example.tcc_reddit.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("categoria/{id}")
    public ResponseEntity<Map<String, Object>> getCategoriaById(@PathVariable int id){
        try{
            Optional<Categoria> categoria = this.categoriaService.getById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("msg", "Sucesso ao recuperar categoria");
            response.put("categoria", categoria.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("msg", "Erro ao recuperar a categoria");
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("categoria")
    public ResponseEntity<Map<String, Object>> storeCategoria(@RequestBody Map<String, String> dados){
        String nome = dados.getOrDefault("nome", null);
        String descricao = dados.getOrDefault("descricao", "");
        try{
            Categoria categoria = this.categoriaService.store(nome, descricao);
            Map<String, Object> response = new HashMap<>();
            response.put("msg", "Categoria criado com sucesso");
            response.put("categoria", categoria);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("msg", "Erro ao criar a categoria");
            errorResponse.put("error", e.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("categoria/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategoria(@PathVariable int id){
        try{
            Optional<Categoria> categoriaDeletada = this.categoriaService.delete(id);
            Map<String, Object> response = new HashMap<>();
            response.put("msg", "categoria deletada com sucesso");
            response.put("categoria", categoriaDeletada);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("msg", "Erro ao deletar categoria");
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/definir-categoria-from-post")
    public List<Map<String, Object>> definirCategorias(@RequestBody Map<String, String> dados) {
        //testar API
        String titulo = dados.getOrDefault("titulo", null);
        String corpo  = dados.getOrDefault("corpo", null);
        return this.categoriaService.definirCategorias(titulo, corpo);
    }
}
