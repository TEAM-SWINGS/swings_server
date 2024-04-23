package com.example.swings.repository;

import com.example.swings.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자 찾기
    Optional<User> findByEmail(String email);

    // 주어진 이메일로 사용자가 존재하는지 확인
    boolean existsByEmail(String email);

    // 주어진 닉네임으로 사용자가 존재하는지 확인
    boolean existsByNickname(String nickname);
}