package com.example.capstone.core.service.impl;

import com.example.capstone.core.model.UserCourseModel;
import com.example.capstone.core.model.UserModel;
import com.example.capstone.core.service.UserService;
import com.example.capstone.core.service.exception.NotFoundException;
import com.example.capstone.infrastucture.entity.Course;
import com.example.capstone.infrastucture.entity.User;
import com.example.capstone.infrastucture.repository.CourseRepository;
import com.example.capstone.infrastucture.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public UserModel addCourseToUser(UserCourseModel model) {
        final User user = userRepository.findById(model.getUserId()).orElseThrow(
                () -> new NotFoundException(String.format("User with id %s not found", model.getUserId())));
        final Course course = courseRepository.findByIdAndIsDeletedFalse(model.getCourseId()).orElseThrow(
                () -> new NotFoundException(String.format("Course with id %s not found", model.getCourseId())));
        user.addCourse(course);
        return modelMapper.map(user, UserModel.class);
    }

}
