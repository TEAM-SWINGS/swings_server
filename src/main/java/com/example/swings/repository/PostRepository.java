package com.example.swings.repository;

import com.example.swings.entity.Post;
import com.example.swings.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByCreatedateDesc(Pageable pageable);

    //Optional<User> findByEmail(String email);
    @Query("SELECT p FROM Post p WHERE p.teamfield = :teamfield")
    Page<Post> findByTeamfield(@Param("teamfield") String teamfield, Pageable pageable);

}