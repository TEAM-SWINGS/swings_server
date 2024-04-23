package com.example.swings.service;

import com.example.swings.dto.PostDTO;
import com.example.swings.dto.UserDTO;
import com.example.swings.entity.Post;
import com.example.swings.entity.User;
import com.example.swings.repository.PostRepository;
import com.example.swings.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final UserRepository userRepository;

    // 게시물을 저장
    public void savePost(PostDTO postDTO) {
        Post post = Post.toPost(postDTO);
        post.setViews(0);
        post.setCreatedate(postDTO.getCreatedate());
        User user = userRepository.findByEmail(postDTO.getEmail()).orElseThrow();
        post.setUser(user);
        postRepository.save(post);
    }

    // ID를 기준으로 게시물을 조회
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + id));
        return PostDTO.fromPost(post);
    }

    // 팀 이름을 기준으로 게시물을 페이지별로 조회
    public Page<PostDTO> getPostsByTeam(String team, Pageable pageable) {
        Page<Post> postPage = postRepository.findByTeamfieldContainingOrderByCreatedateDesc(team, pageable);
        List<PostDTO> postDTOList = postPage.getContent().stream()
                .map(PostDTO::fromPost)
                .collect(Collectors.toList());
        return new PageImpl<>(postDTOList, pageable, postPage.getTotalElements());
    }

    // 모든 게시물을 페이지별로 조회
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAllByOrderByCreatedateDesc(pageable);
        List<PostDTO> postDTOList = postPage.getContent().stream()
                .map(PostDTO::fromPost)
                .collect(Collectors.toList());
        return new PageImpl<>(postDTOList, pageable, postPage.getTotalElements());
    }

    // 게시물 조회수를 증가
    public boolean increaseViews(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setViews(post.getViews() + 1);
            postRepository.save(post);
            return true;
        } else {
            return false;
        }
    }

    // 게시물을 수정
    @Transactional
    public void updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + id));

        // 수정된 내용 업데이트
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setTeamfield(postDTO.getTeamfield());

        postRepository.save(post);
    }

    // 게시글을 삭제
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + id));

        postRepository.delete(post);
    }

    // 주어진 사용자가 해당 게시물의 소유자인지 확인
    public boolean isPostOwner(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return post.getUser().getId().equals(userId);
    }

    // 게시글을 작성한 사용자의 ID를 가져오기
    public Long getPostOwnerId(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        return postOptional.map(post -> post.getUser().getId())
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + postId));
    }
}