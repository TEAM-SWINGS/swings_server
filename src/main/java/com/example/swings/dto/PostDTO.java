package com.example.swings.dto;

import com.example.swings.entity.Post;
import com.example.swings.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
//    private User user;
    private Long id;
    private String nickname;
    private String email;
    private String teamfield; // 데이터베이스 컬럼명에 맞게 수정
    private String title; // 데이터베이스 컬럼명에 맞게 수정
    private String content; // 데이터베이스 컬럼명에 맞게 수정
    private Integer views; // 데이터베이스 컬럼명에 맞게 수정
    private LocalDateTime createdate; // 데이터베이스 컬럼명에 맞게 수정

    public static PostDTO fromPost(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        // User가 null인 경우에 대비하여 null 체크를 추가합니다.
        if (post.getUser() != null) {
            postDTO.setNickname(post.getUser().getNickname());
            postDTO.setEmail(post.getUser().getEmail());
        }
        postDTO.setTeamfield(post.getTeamfield());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setViews(post.getViews());
        postDTO.setCreatedate(post.getCreatedate());
        return postDTO;
    }
}