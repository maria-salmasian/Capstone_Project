package com.example.capstone.core.service.impl;

import com.example.capstone.core.model.CourseModel;
import com.example.capstone.core.service.CourseService;
import com.example.capstone.core.service.exception.NotFoundException;
import com.example.capstone.core.service.exception.ResourceAlreadyExistsException;
import com.example.capstone.infrastucture.entity.Cluster;
import com.example.capstone.infrastucture.entity.Course;
import com.example.capstone.infrastucture.repository.ClusterRepository;
import com.example.capstone.infrastucture.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    private final ClusterRepository clusterRepository;

    @Transactional
    public CourseModel createCourse(CourseModel courseModel) {
        courseRepository.findByTitleAndIsDeletedFalse(courseModel.getTitle()).ifPresent(existingCourse ->
        {
            throw new ResourceAlreadyExistsException(String.format("Course with title %s already exists", existingCourse.getTitle()));
        });

        final Set<Cluster> clusters = new HashSet<>(clusterRepository.findAllByIdIn(courseModel.getClusterIds()));
        final Course course = modelMapper.map(courseModel, Course.class);
        course.setClusters(clusters);
        return modelMapper.map(courseRepository.save(course), CourseModel.class);
    }

    @Transactional
    public void deleteCourse(Long id) {
        final Course course = courseRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new NotFoundException(String.format("Course with id %s not found", id)));
        courseRepository.delete(course);
    }

    @Transactional
    public CourseModel getCourse(Long id) {
        final Course course = courseRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new NotFoundException(String.format("Course with id %s not found", id)));
        return modelMapper.map(course, CourseModel.class);
    }

    public List<CourseModel> getCourses(Pageable pageable) {
        return courseRepository.findAllByIsDeletedFalse(pageable)
                .stream()
                .map(c -> modelMapper.map(c, CourseModel.class))
                .toList();
    }

    public List<CourseModel> getCoursesByUser(Long userId){
        return courseRepository.findAllByIsDeletedFalseAndUsersIdEquals(userId)
                .stream()
                .map(c -> modelMapper.map(c, CourseModel.class))
                .toList();
    }


    public List<CourseModel> getCoursesByUserAndClusterName(Pageable pageable, Long userId, String clusterName){
        return courseRepository.findCourseByUsersIdEqualsAndClustersNameLike(pageable, userId, clusterName+'%')
                .stream()
                .map(c -> modelMapper.map(c, CourseModel.class))
                .toList();
    }

}
