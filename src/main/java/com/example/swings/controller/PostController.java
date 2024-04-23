package com.example.swings.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.CacheControl;
import com.example.swings.dto.PostDTO;
import com.example.swings.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@RestController
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 생성
    @PostMapping("/api/posts/create")
    public ResponseEntity<String> createPost(@RequestBody PostDTO postDTO) {
        try {
            postService.savePost(postDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create post.");
        }
    }

    // 게시글 수정
    @PutMapping("api/posts/edit/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        try {

            postService.updatePost(id, postDTO);
            return ResponseEntity.ok("Post updated successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 게시글 삭제
    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.ok("게시물이 삭제되었습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 게시글 목록 조회
    @GetMapping("/api/posts")
    public ResponseEntity<Page<PostDTO>> getPosts(@RequestParam(required = false) String team,
                                                  @RequestParam(required = false) String sort,
                                                  @RequestParam(required = false) String search,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        // 페이지 정보 설정
        Pageable pageable;
        if (sort != null) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        } else {
            pageable = PageRequest.of(page, size);
        }

        // 팀이 지정되었는지에 따라 게시물 가져오기
        Page<PostDTO> postsPage;
        if (team != null) {
            postsPage = postService.getPostsByTeam(team, pageable);
        } else {
            postsPage = postService.getAllPosts(pageable);
        }

        return ResponseEntity.ok(postsPage);
    }

    // 게시글 조회수 증가
    @PutMapping("/api/posts/views")
    public ResponseEntity<String> addViews(@RequestParam Long id) {
        boolean success = postService.increaseViews(id);
        if (success) {
            return ResponseEntity.ok("Views increased successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with id: " + id);
        }
    }

    // 게시글 상세 조회
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
