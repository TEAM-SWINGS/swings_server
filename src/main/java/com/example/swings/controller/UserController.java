package com.example.swings.controller;


import com.example.swings.dto.UserDTO;
import com.example.swings.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Controller
@RequiredArgsConstructor //MemberService에 대한 멤버를 사용 가능
public class UserController {

    // 생성자 주입
    private final UserService userService;
    @PostMapping("/user/save")
    public ResponseEntity<Void> save(@ModelAttribute UserDTO userDTO) {
        System.out.println("userController.save");
        System.out.println("userDTO = " + userDTO);
        userService.save(userDTO);

        // 리다이렉션을 위한 응답을 생성하고, 리다이렉션할 URL을 Location 헤더에 설정
        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create("http://192.168.240.41:3000/login"));

        // HTTP 상태 코드 302(Found)를 사용하여 리다이렉션을 수행
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @ResponseBody
    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        boolean isAuthenticated = userService.authenticate(userDTO);
        if (isAuthenticated) {
            // 리다이렉션을 위한 응답을 생성하고, 리다이렉션할 URL을 Location 헤더에 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("http://localhost:3000/"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 잘못되었습니다.");
        }
    }


}