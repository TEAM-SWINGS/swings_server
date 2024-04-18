package com.example.swings.repository;

import com.example.swings.entity.Post;
import com.example.swings.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedateDesc();
    //Optional<User> findByEmail(String email);
    @Query("SELECT p FROM Post p WHERE p.teamfield = :teamfield")
    List<Post> findByTeamfield(@Param("teamfield") String teamfield);

}