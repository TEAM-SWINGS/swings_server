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
    private Long id; // 게시물 id
    private Long userId; // 사용자 id
    private String nickname; // 닉네임
    private String email; // 이메일
    private String teamfield; // 팀 명
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private Integer views; // 게시글 조회수
    private LocalDateTime createdate; // 게시글 생성일
    private int pageNumber; // 페이지 번호
    private int pageSize; // 페이지 크기

    // Post 엔티티로부터 PostDTO 생성
    public static PostDTO fromPost(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        // User가 null인 경우에 대비하여 null 체크를 추가
        if (post.getUser() != null) {
            postDTO.setUserId(post.getUser().getId());
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