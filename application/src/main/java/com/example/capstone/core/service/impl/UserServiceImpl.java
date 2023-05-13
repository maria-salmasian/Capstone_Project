package com.example.capstone.core.service.impl;

import com.example.capstone.core.model.CourseModel;
import com.example.capstone.core.model.UserCourseModel;
import com.example.capstone.core.model.UserModel;
import com.example.capstone.core.model.event.CreateUserEvent;
import com.example.capstone.core.service.UserService;
import com.example.capstone.core.service.exception.NotFoundException;
import com.example.capstone.core.service.exception.ValidationException;
import com.example.capstone.infrastucture.entity.Course;
import com.example.capstone.infrastucture.entity.User;
import com.example.capstone.infrastucture.repository.CourseRepository;
import com.example.capstone.infrastucture.repository.RoleRepository;
import com.example.capstone.infrastucture.repository.UserRepository;
import com.example.capstone.utils.enumeration.RoleName;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.capstone.core.service.impl.SecurityServiceImpl.getClaims;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final RoleRepository roleRepository;
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

    @Override
    public void delete(Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("User with id %s not found", userId)));
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public UserModel getUser(String token) {
        final Claims claims = (getClaims(token));
        final String email = claims.get("email").toString();

        final User user = userRepository.findUserByUsername(email).orElseThrow(
                () -> new NotFoundException(String.format("User with username %s not found", email)));
        return modelMapper.map(user, UserModel.class);
    }

    @Override
    public List<UserModel> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, UserModel.class))
                .toList();    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void saveUser(final CreateUserEvent event) {
        Claims claims = event.getClaims();
        String email = claims.get("email").toString();
        final User user = userRepository.findUserByUsername(email)
                .orElseGet(() -> User.builder()
                        .name(claims.get("given_name").toString())
                        .lastName(claims.get("family_name").toString())
                        .role(roleRepository.getRoleByRoleName(getUserRole(email)))
                        .username(email)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .enabled(true)
                        .deleted(false)
                        .build());
        if (user.isDeleted())
            throw new ValidationException(String.format("User with email %s is deleted", email));
        if (user.getId() == null) {
            userRepository.save(user);
        }
    }

    public static RoleName getUserRole(String email) {
        if (email.contains("@edu.aua.am"))
            return RoleName.STUDENT;
        else return RoleName.PROFESSOR;
    }
}
