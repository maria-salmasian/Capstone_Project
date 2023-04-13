package com.example.capstone.infrasturcture.repository;

import com.example.capstone.infrasturcture.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
