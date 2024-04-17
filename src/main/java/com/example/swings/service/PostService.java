package com.example.swings.service;

import com.example.swings.dto.PostDTO;
import com.example.swings.dto.UserDTO;
import com.example.swings.entity.Post;
import com.example.swings.entity.User;
import com.example.swings.repository.PostRepository;
import com.example.swings.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service //스프링이 관리해주는 객체 == 스프링 빈
@RequiredArgsConstructor //controller와 같이. final 멤버변수 생성자 만드는 역할
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    public void savePost(PostDTO postDTO) {
        Post post = Post.toPost(postDTO);
        post.setViews(0); // 새로운 글이므로 조회수는 0으로 초기화
        post.setCreatedate(LocalDateTime.now()); // 현재 시간으로 생성일 설정
        postRepository.save(post);
    }

    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        return PostDTO.fromPost(post);
    }

    public Long findUserIdByPostId(Long postId){
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            return post.getUserID();
        }
        return null;
    }


    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll(); // 모든 게시글을 가져옴
        // Post 엔티티를 PostDTO로 변환하여 리스트로 반환
        return posts.stream()
                .map(PostDTO::fromPost)
                .collect(Collectors.toList());
    }

}