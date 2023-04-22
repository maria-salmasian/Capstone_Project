package com.example.capstone.infrastucture.repository;

import com.example.capstone.infrastucture.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByTitleAndIsDeletedFalse(String title);

    Optional<Course> findByIdAndIsDeletedFalse(Long id);

    Page<Course> findAllByDeletedFalse(Pageable pageable);
}
