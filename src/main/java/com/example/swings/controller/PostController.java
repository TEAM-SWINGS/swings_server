package com.example.swings.controller;

import com.example.swings.dto.PostDTO;
import com.example.swings.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/posts/create")
    public ResponseEntity<String> createPost(@RequestBody PostDTO postDTO) {
        try {
            postService.savePost(postDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create post.");
        }
    }

    @GetMapping("/api/posts") // GET 요청을 처리하는 엔드포인트 추가
    public ResponseEntity<?> getAllPosts() {
        try {
            List<PostDTO> posts = postService.getAllPosts(); // 모든 게시글을 가져옴
            return ResponseEntity.ok(posts); // 게시글 리스트를 응답으로 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
