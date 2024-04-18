package com.example.swings.controller;

import com.example.swings.dto.PostDTO;
import com.example.swings.entity.Post;
import com.example.swings.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/api/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/api/posts/views") // GET 요청을 처리하는 엔드포인트 추가
    public void addViews(PostDTO postDTO) {
        postService.increaseViews(postDTO);
    }

    @GetMapping("/api/posts/{team}")
    public ResponseEntity<List<PostDTO>> getPostsByTeam(@PathVariable String team) {
        List<PostDTO> filteredPosts = postService.getPostsByTeam(team);
        return ResponseEntity.ok(filteredPosts);
    }








}
