package com.example.tcc_reddit.repository;

import com.example.tcc_reddit.model.SubRedditPostArquivoBruto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubRedditPostArquivoBrutoRepository extends JpaRepository<SubRedditPostArquivoBruto, Long> {
    //
}
