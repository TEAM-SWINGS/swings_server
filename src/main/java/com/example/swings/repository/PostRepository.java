package com.example.swings.repository;

import com.example.swings.entity.Post;
import com.example.swings.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //Optional<User> findByEmail(String email);

}