package com.example.capstone.ws.controller;

import com.example.capstone.core.model.UserCourseModel;
import com.example.capstone.core.model.UserModel;
import com.example.capstone.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/courses")
    public ResponseEntity<UserModel> addCourseToUser(@RequestBody UserCourseModel userCourseModel) {
        return ResponseEntity.ok().body(userService.addCourseToUser(userCourseModel));
    }
}
