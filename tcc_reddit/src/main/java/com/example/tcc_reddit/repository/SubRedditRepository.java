package com.example.tcc_reddit.repository;

import com.example.tcc_reddit.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubRedditRepository extends JpaRepository<SubReddit, String> {
}
