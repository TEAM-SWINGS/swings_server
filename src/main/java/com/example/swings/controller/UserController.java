package com.example.swings.controller;


import com.example.swings.dto.ChangePasswordRequest;
import com.example.swings.dto.PostDTO;
import com.example.swings.dto.UserDTO;
import com.example.swings.entity.User;
import com.example.swings.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


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
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO) {
        User user = userService.authenticate(userDTO);
        if (user != null) {
            // 로그인이 성공했을 때, 사용자 정보를 포함한 JSON 응답 반환
            Map<String, Object> response = new HashMap<>();
            response.put("message", "로그인에 성공했습니다.");
            response.put("user", user);
            response.put("redirectUrl", "http://192.168.240.41:3000/"); // 로그인 성공 후 리다이렉트할 URL

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } else {
            // 로그인이 실패했을 때, 오류 메시지를 포함한 JSON 응답 반환
            Map<String, Object> response = new HashMap<>();
            response.put("error", "이메일 또는 비밀번호가 잘못되었습니다.");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(response, headers, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/user/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        if (!userService.isPasswordCorrect(Long.valueOf(request.getUserId()), request.getCurrentPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect.");
        }

        userService.changePassword(Long.valueOf(request.getUserId()), request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully.");
    }



}