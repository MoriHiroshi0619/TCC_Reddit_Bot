package com.example.tcc_reddit.repository;

import com.example.tcc_reddit.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository <Categoria, Integer> {
    //
}
