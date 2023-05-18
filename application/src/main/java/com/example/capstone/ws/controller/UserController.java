package com.example.capstone.ws.controller;

import com.example.capstone.config.ClientConfig;
import com.example.capstone.core.model.UserCourseModel;
import com.example.capstone.core.model.UserModel;
import com.example.capstone.core.service.AttentionService;
import com.example.capstone.core.service.SecurityService;
import com.example.capstone.core.service.UserService;
import com.example.capstone.utils.annotation.IsAuthenticated;
import com.example.capstone.ws.dto.AverageAttentionDto;
import com.example.capstone.ws.dto.TokenDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/user")
    public ResponseEntity<UserModel> getUser(@IsAuthenticated @Valid @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(userService.getUser(token));
    }

    @GetMapping()
    public ResponseEntity<List<UserModel>> getUsers(@IsAuthenticated @Valid @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("{userId}/courses/{courseId}/attention-average")
    public ResponseEntity<AverageAttentionDto> getAverageByUserAndCourse(
            @IsAuthenticated @Valid @RequestHeader("Authorization") String token,
            @PathVariable Long userId,
            @PathVariable Long courseId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok().body(attentionService.getAverageByUserAndCourseAndDate(userId, courseId, date.atStartOfDay()));
    }


    @GetMapping("/{userId}/courses/{courseId}/attention-average-by-interval")
    public ResponseEntity<List<Double>> getAverageByCourseDateInterval(@IsAuthenticated @Valid @RequestHeader("Authorization") String token, @PathVariable Long userId, @PathVariable Long courseId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok().body(attentionService.getAverageByUserAndCourseAndDateINTERVAL(userId, courseId, startDate.atStartOfDay(), endDate.atStartOfDay()));
    }


    @GetMapping("/login")
    public ResponseEntity<TokenDto> login(
            @Valid @NotNull @RequestParam(name = "code") final String code) {
        return ResponseEntity.ok().body(securityService.exchangeAuthorizationCode(code));

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
