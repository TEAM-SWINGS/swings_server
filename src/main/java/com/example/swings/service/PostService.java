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
        Page<Post> postPage = postRepository.findByTeamfield(team, pageable);
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
}