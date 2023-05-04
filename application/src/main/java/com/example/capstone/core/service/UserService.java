package com.example.capstone.core.service;

import com.example.capstone.core.model.UserCourseModel;
import com.example.capstone.core.model.UserModel;

public interface UserService {
    UserModel addCourseToUser(UserCourseModel userCourseModel);

    void delete(Long userId);
}
