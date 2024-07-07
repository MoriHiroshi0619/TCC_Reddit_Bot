package com.example.tcc_reddit.repository;

import com.example.tcc_reddit.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MunicipioRepository extends JpaRepository <Municipio, String> {
    @Query(value = "SELECT * FROM municipios ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Municipio getRandomMunicipio();
}
