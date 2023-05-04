package com.example.capstone.ws.controller;

import com.example.capstone.core.model.UserCourseModel;
import com.example.capstone.core.model.UserModel;
import com.example.capstone.core.service.AttentionService;
import com.example.capstone.core.service.UserService;
import com.example.capstone.ws.dto.AverageAttentionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AttentionService attentionService;

    @PostMapping("/courses")
    public ResponseEntity<UserModel> addCourseToUser(@RequestBody UserCourseModel userCourseModel) {
        return ResponseEntity.ok().body(userService.addCourseToUser(userCourseModel));
    }

    @GetMapping("{userId}/courses/{courseId}/attention-average")
    public ResponseEntity<AverageAttentionDto> getAverageByUserAndCourse(
            @PathVariable Long userId,
            @PathVariable Long courseId,
            @RequestParam LocalDateTime date) {
        return ResponseEntity.ok().body(attentionService.getAverageByUserAndCourseAndDate(userId, courseId, date));
    }
}
