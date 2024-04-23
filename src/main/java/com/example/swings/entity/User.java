package com.example.swings.entity;

import com.example.swings.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자 id

    @Column(unique = true, name = "email")
    private String email; // 이메일

    @Column(name = "password")
    private String password; // 비밀번호

    @Column(unique = true, name = "nickname")
    private String nickname; // 닉네임

    // UserDTO로부터 User 엔티티 생성
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setNickname(userDTO.getNickname());
        return user;
    }
}