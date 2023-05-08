package com.example.capstone.ws.controller;

import com.example.capstone.core.model.CourseModel;
import com.example.capstone.core.service.AttentionService;
import com.example.capstone.core.service.CourseService;
import com.example.capstone.utils.annotation.IsAuthenticated;
import com.example.capstone.ws.dto.AverageAttentionDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Validated
public class CourseController {

    private final CourseService courseService;
    private final AttentionService attentionService;

    @PostMapping
    public ResponseEntity<CourseModel> createCourse(@IsAuthenticated @Valid @RequestHeader("Authorization") String token, @RequestBody CourseModel model) {
        return ResponseEntity.ok().body(courseService.createCourse(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@IsAuthenticated @Valid @RequestHeader("Authorization") String token, @PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseModel> getCourse(@IsAuthenticated @Valid @RequestHeader("Authorization") String token, @PathVariable Long id) {
        return ResponseEntity.ok().body(courseService.getCourse(id));
    }

    @GetMapping()
    public ResponseEntity<List<CourseModel>> getCourses(@IsAuthenticated @Valid @RequestHeader("Authorization") String token, Pageable pageable) {
        return ResponseEntity.ok().body(courseService.getCourses(pageable));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CourseModel>> getCoursesByUser(@IsAuthenticated @Valid @RequestHeader("Authorization") String token, @PathVariable Long userId) {
        return ResponseEntity.ok().body(courseService.getCoursesByUser(userId));
    }

    @GetMapping("/{courseId}/attention-average")
    public ResponseEntity<AverageAttentionDto> getAverageByCourse(@IsAuthenticated @Valid @RequestHeader("Authorization") String token, @PathVariable Long courseId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok().body(attentionService.getAverageByCourseAndDate(courseId, date.atStartOfDay()));
    }

    @GetMapping("/users/{userId}/cluster/{clusterName}")
    public ResponseEntity<List<CourseModel>> getCoursesByUserIDAndCluster(@IsAuthenticated @Valid @RequestHeader("Authorization") String token, Pageable pageable, @PathVariable Long userId, @PathVariable String clusterName) {
        return ResponseEntity.ok().body(courseService.getCoursesByUserAndClusterName(pageable, userId, clusterName));
    }

}
