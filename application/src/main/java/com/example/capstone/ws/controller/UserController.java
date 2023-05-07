package com.example.capstone.ws.controller;

import com.example.capstone.config.ClientConfig;
import com.example.capstone.core.model.UserCourseModel;
import com.example.capstone.core.model.UserModel;
import com.example.capstone.core.service.AttentionService;
import com.example.capstone.core.service.SecurityService;
import com.example.capstone.core.service.UserService;
import com.example.capstone.utils.annotation.IsAuthenticated;
import com.example.capstone.ws.dto.AverageAttentionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@Validated
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AttentionService attentionService;
    private final SecurityService securityService;
    private final ClientConfig clientConfig;

    @PostMapping("/courses")
    public ResponseEntity<UserModel> addCourseToUser(@IsAuthenticated @Valid @RequestHeader("Authorization") String token, @RequestBody UserCourseModel userCourseModel) {
        return ResponseEntity.ok().body(userService.addCourseToUser(userCourseModel));
    }

    @GetMapping("{userId}/courses/{courseId}/attention-average")
    public ResponseEntity<AverageAttentionDto> getAverageByUserAndCourse(
            @IsAuthenticated @Valid @RequestHeader("Authorization") String token,
            @PathVariable Long userId,
            @PathVariable Long courseId,
            @RequestParam LocalDateTime date) {
        return ResponseEntity.ok().body(attentionService.getAverageByUserAndCourseAndDate(userId, courseId, date));
    }

    @GetMapping("/login")
    public String login(
            @Valid @NotNull @RequestParam(name = "code") final String code) {
        return securityService.exchangeAuthorizationCode(code);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@IsAuthenticated @Valid @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).location(URI.create(clientConfig.getLogoutUrl())).build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<HttpStatus> delete(@IsAuthenticated @Valid @RequestHeader("Authorization") String token, @PathVariable Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
