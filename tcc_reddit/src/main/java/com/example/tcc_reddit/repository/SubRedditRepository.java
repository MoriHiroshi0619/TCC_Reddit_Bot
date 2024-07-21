package com.example.tcc_reddit.repository;

import com.example.tcc_reddit.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubRedditRepository extends JpaRepository<SubReddit, String> {


    Optional<SubReddit> findFirstBySubRedditName(String nome);

    Optional<SubReddit> findFirstBySubRedditId(String id);
}
