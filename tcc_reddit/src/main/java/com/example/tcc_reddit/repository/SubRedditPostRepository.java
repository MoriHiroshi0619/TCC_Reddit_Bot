package com.example.tcc_reddit.repository;

import com.example.tcc_reddit.model.SubRedditPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubRedditPostRepository extends JpaRepository<SubRedditPost, String> {
    //
}
