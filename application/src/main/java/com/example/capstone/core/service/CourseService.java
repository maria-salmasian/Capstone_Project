package com.example.capstone.core.service;

import com.example.capstone.core.model.CourseModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    CourseModel createCourse(CourseModel courseModel);

    void deleteCourse(Long id);

    CourseModel getCourse(Long id);

    List<CourseModel> getCourses(Pageable pageable);
}
