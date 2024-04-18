package com.example.swings.entity;

import com.example.swings.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "email") // 데이터베이스 컬럼명에 맞게 수정
    private String email;

    @Column(name = "password") // 데이터베이스 컬럼명에 맞게 수정
    private String password;

    @Column(unique = true, name = "nickname") // 데이터베이스 컬럼명에 맞게 수정
    private String nickname;

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setNickname(userDTO.getNickname());
        return user;
    }
}