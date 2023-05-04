package com.example.capstone.ws.controller;

import com.example.capstone.core.model.CourseModel;
import com.example.capstone.core.service.AttentionService;
import com.example.capstone.core.service.CourseService;
import com.example.capstone.ws.dto.AverageAttentionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final AttentionService attentionService;

    @PostMapping
    public ResponseEntity<CourseModel> createCourse(@RequestBody CourseModel model) {
        return ResponseEntity.ok().body(courseService.createCourse(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseModel> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok().body(courseService.getCourse(id));
    }

    @GetMapping()
    public ResponseEntity<List<CourseModel>> getCourses(Pageable pageable) {
        return ResponseEntity.ok().body(courseService.getCourses(pageable));
    }

    @GetMapping("/{courseId}/attention-average")
    public ResponseEntity<AverageAttentionDto> getAverageByCourse(@PathVariable Long courseId, @RequestParam LocalDateTime date) {
        return ResponseEntity.ok().body(attentionService.getAverageByCourseAndDate(courseId, date));
    }
}
