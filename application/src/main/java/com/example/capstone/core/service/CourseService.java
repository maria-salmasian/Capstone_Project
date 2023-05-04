package com.example.capstone.core.service;

import com.example.capstone.core.model.CourseModel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CourseService {

    CourseModel createCourse(CourseModel courseModel);

    void deleteCourse(Long id);

    CourseModel getCourse(Long id);

    List<CourseModel> getCourses(Pageable pageable);
}
