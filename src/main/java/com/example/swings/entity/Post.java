package com.example.swings.entity;

import com.example.swings.dto.PostDTO;
import com.example.swings.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

//import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "teamfield") // 데이터베이스 컬럼명에 맞게 수정
    private String teamfield;

    @Column(name = "title") // 데이터베이스 컬럼명에 맞게 수정
    private String title;

    @Column(name = "content") // 데이터베이스 컬럼명에 맞게 수정
    private String content;

    @Column(name = "views")
    private Integer views;

    @Column(name = "createdate")
    private LocalDateTime createdate;

    public static Post toPost(PostDTO postDTO) {
        Post post = new Post();
//        post.setUser(postDTO.getUser());
        post.setTeamfield(postDTO.getTeamfield());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setViews(postDTO.getViews());
        post.setCreatedate(postDTO.getCreatedate());
        return post;
    }
}