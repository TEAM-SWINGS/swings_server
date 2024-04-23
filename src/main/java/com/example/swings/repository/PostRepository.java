package com.example.swings.repository;

import com.example.swings.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 생성일자를 기준으로 내림차순으로 모든 게시물을 페이지별로 조회
    Page<Post> findAllByOrderByCreatedateDesc(Pageable pageable);

    // 팀 이름을 포함하고 있는 게시물을 팀 이름을 기준으로 내림차순으로 페이지별로 조회
    @Query("SELECT p FROM Post p WHERE p.teamfield LIKE %:team% ORDER BY p.createdate DESC")
    Page<Post> findByTeamfieldContainingOrderByCreatedateDesc(@Param("team") String team, Pageable pageable);
}