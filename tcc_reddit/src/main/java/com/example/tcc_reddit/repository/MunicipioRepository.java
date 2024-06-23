package com.example.tcc_reddit.repository;

import com.example.tcc_reddit.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipioRepository extends JpaRepository <Municipio, String> {
    //
}
