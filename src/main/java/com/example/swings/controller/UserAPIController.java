package com.example.swings.controller;

import com.example.swings.entity.User;
import com.example.swings.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserAPIController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/select", method = {RequestMethod.GET, RequestMethod.POST})
    public List<User> selectAll() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/insert", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public User insert(@RequestBody Map<String, String> map) {
        User user = new User();
        user.setEmail(map.get("email"));
        user.setPassword(map.get("password"));
        user.setNickname(map.get("nickname"));

        return userRepository.save(user);
    }
}
