package com.example.swings.controller;

import org.springframework.http.CacheControl;
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
import java.util.concurrent.TimeUnit;
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

    @PutMapping("/api/posts/views")
    public ResponseEntity<String> addViews(@RequestParam Long id) {
        boolean success = postService.increaseViews(id);
        if (success) {
            return ResponseEntity.ok("Views increased successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with id: " + id);
        }
    }

    @GetMapping("/api/posts/{team}")
    public ResponseEntity<List<PostDTO>> getPostsByTeam(@PathVariable String team) {
        List<PostDTO> filteredPosts = postService.getPostsByTeam(team);
        return ResponseEntity.ok(filteredPosts);
    }

    @GetMapping("/postpage/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        try {
            PostDTO post = postService.getPostById(id);
            // Cache-Control 헤더 설정
            CacheControl cacheControl = CacheControl.maxAge(0, TimeUnit.SECONDS).noCache().mustRevalidate();
            return ResponseEntity.ok().cacheControl(cacheControl).body(post);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }






}
