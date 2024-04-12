package com.example.swings.dto;

import com.example.swings.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email; // 데이터베이스 컬럼명에 맞게 수정
    private String password; // 데이터베이스 컬럼명에 맞게 수정
    private String nickname; // 데이터베이스 컬럼명에 맞게 수정

    public static UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setNickname(user.getNickname());
        return userDTO;
    }

//    public static UserDTO toUserDTO(User user){
//        UserDTO userDTO = new UserDTO();
//        //userDTO.setId(user.getId());
//        userDTO.setUserEmail(user.getUserEmail());
//        userDTO.setUserName(user.getUserName());
//        userDTO.setUserPwd(user.getUserPwd());
//
//        return userDTO;
//    }
}