package com.example.swings.entity;

import com.example.swings.dto.PostDTO;
import com.example.swings.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시물 ID

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user; // 사용자

    @Column(name = "teamfield") // 팀 명
    private String teamfield;

    @Column(name = "title") // 제목
    private String title;

    @Column(name = "content") // 내용
    private String content;

    @Column(name = "views") // 조회수
    private Integer views;

    @CreationTimestamp
    @Column(name = "createdate") // 게시글 생성일
    private LocalDateTime createdate;

    // PostDTO로부터 Post 엔티티 생성
    public static Post toPost(PostDTO postDTO) {
        Post post = new Post();
        post.setTeamfield(postDTO.getTeamfield());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setViews(postDTO.getViews());
        return post;
    }
}