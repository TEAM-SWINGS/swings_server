package com.example.swings.controller;

import com.example.swings.dto.PostDTO;
import com.example.swings.entity.Post;
import com.example.swings.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;
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
    public ResponseEntity<Page<PostDTO>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> postPage = postService.getAllPosts(pageable);

        return ResponseEntity.ok(postPage);
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

    @GetMapping("/postpage/{id}") // 여기서 postId가 아니라 id로 변경
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) { // 여기서도 postId가 아니라 id로 변경
        try {
            PostDTO post = postService.getPostById(id); // 여기서도 postId가 아니라 id로 변경
            return ResponseEntity.ok(post);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }






}
