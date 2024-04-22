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

@Service //스프링이 관리해주는 객체 == 스프링 빈
@RequiredArgsConstructor //controller와 같이. final 멤버변수 생성자 만드는 역할
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final UserRepository userRepository;

    public void savePost(PostDTO postDTO) {
        Post post = Post.toPost(postDTO);
        post.setViews(0);
        post.setCreatedate(postDTO.getCreatedate()); // 요청에서 받은 createdate 설정
        User user = userRepository.findByEmail(postDTO.getEmail()).orElseThrow();
        post.setUser(user);
        postRepository.save(post);
    }

    public PostDTO getPostById(Long id) { // 여기서도 postId가 아니라 id로 변경
        Post post = postRepository.findById(id) // 여기서도 postId가 아니라 id로 변경
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + id)); // 여기서도 postId가 아니라 id로 변경
        return PostDTO.fromPost(post);
    }

    public Page<PostDTO> getPostsByTeam(String team, Pageable pageable) {
        Page<Post> postPage = postRepository.findByTeamfieldContainingOrderByCreatedateDesc(team, pageable);
        List<PostDTO> postDTOList = postPage.getContent().stream()
                .map(PostDTO::fromPost)
                .collect(Collectors.toList());
        return new PageImpl<>(postDTOList, pageable, postPage.getTotalElements());
    }

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAllByOrderByCreatedateDesc(pageable);
        List<PostDTO> postDTOList = postPage.getContent().stream()
                .map(PostDTO::fromPost)
                .collect(Collectors.toList());
        return new PageImpl<>(postDTOList, pageable, postPage.getTotalElements());
    }

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

    @Transactional
    public void updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + id));

        // 수정된 내용 업데이트
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setTeamfield(postDTO.getTeamfield()); // TeamField 업데이트 추가

        // 그 외 필요한 필드들도 업데이트

        postRepository.save(post);
    }


    // 게시글 삭제 메서드
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + id));

        postRepository.delete(post);
    }

    public boolean isPostOwner(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return post.getUser().getId().equals(userId);
    }

    // 게시글을 작성한 사용자의 ID를 가져오는 메서드
    public Long getPostOwnerId(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        return postOptional.map(post -> post.getUser().getId())
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + postId));
    }
}