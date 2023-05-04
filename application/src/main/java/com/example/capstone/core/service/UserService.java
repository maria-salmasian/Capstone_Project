package com.example.capstone.core.service;

import com.example.capstone.core.model.CourseModel;
import com.example.capstone.core.model.UserCourseModel;
import com.example.capstone.core.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserModel addCourseToUser(UserCourseModel userCourseModel);
}
