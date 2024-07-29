package com.example.tcc_reddit.repository;

import com.example.tcc_reddit.model.SubRedditPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubRedditPostRepository extends JpaRepository<SubRedditPost, String> {

    public Optional<SubRedditPost> findFirstByPostId(String id);
}
