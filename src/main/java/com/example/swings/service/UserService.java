package com.example.swings.service;

import com.example.swings.dto.UserDTO;
import com.example.swings.entity.User;
import com.example.swings.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; // 먼저 jpa, mysql dependency 추가

    // 사용자 정보를 저장
    public void save(UserDTO userDTO) {
        User user = User.toUser(userDTO);
        userRepository.save(user);
    }

    // 사용자 인증을 수행
    public User authenticate(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElse(null);
        if (user != null) {
            // 이메일에 해당하는 사용자가 존재하는 경우
            return user.getPassword().equals(userDTO.getPassword()) ? user : null;
        } else {
            // 이메일에 해당하는 사용자가 존재하지 않는 경우
            return null;
        }
    }

    // 사용자 비밀번호가 올바른지 확인
    public boolean isPasswordCorrect(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return user.getPassword().equals(password);
    }

    // 사용자 비밀번호를 변경
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    // 주어진 이메일이 이미 존재하는지 확인
    public boolean isEmailExists(String email) {
        // 이메일이 이미 존재하는지 여부를 확인
        return userRepository.existsByEmail(email);
    }

    // 주어진 닉네임이 이미 존재하는지 확인
    public boolean isNicknameExists(String nickname) {
        // 닉네임이 이미 존재하는지 여부를 확인
        return userRepository.existsByNickname(nickname);
    }
}