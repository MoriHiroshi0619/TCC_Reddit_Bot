package com.example.tcc_reddit.repository;

import com.example.tcc_reddit.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository <Categoria, Integer> {
    List<Categoria> findByNomeIn(List<String> nomes);

    Optional<Categoria> findFirstByNome(String nome);
}
