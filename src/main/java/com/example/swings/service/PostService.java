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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
        post.setCreatedate(LocalDateTime.now());
        User user = userRepository.findByEmail(postDTO.getEmail()).orElseThrow();
        post.setUser(user);
        postRepository.save(post);
    }

    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        return PostDTO.fromPost(post);
    }

    public List<PostDTO> getPostsByTeam(String team) {
        return postRepository.findByTeamfield(team).stream()
                .map(PostDTO::fromPost)
                .collect(Collectors.toList());
    }


    public List<PostDTO> getAllPosts() {
        return postRepository.findAllByOrderByCreatedateDesc().stream()
                .map(PostDTO::fromPost)
                .collect(Collectors.toList());
    }


    public void increaseViews(PostDTO postDTO) {
        Post post = postRepository.findById(postDTO.getId()).orElseThrow();
        int views = post.getViews() + 1;
        post.setViews(views);
        postRepository.save(post);
    }
}