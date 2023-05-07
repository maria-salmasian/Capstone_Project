package com.example.capstone.core.service.impl;

import com.example.capstone.core.model.UserCourseModel;
import com.example.capstone.core.model.UserModel;
import com.example.capstone.core.model.event.CreateUserEvent;
import com.example.capstone.core.service.exception.NotFoundException;
import com.example.capstone.infrastucture.entity.Course;
import com.example.capstone.infrastucture.entity.Role;
import com.example.capstone.infrastucture.entity.User;
import com.example.capstone.infrastucture.repository.CourseRepository;
import com.example.capstone.infrastucture.repository.RoleRepository;
import com.example.capstone.infrastucture.repository.UserRepository;
import com.example.capstone.utils.enumeration.RoleName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.example.capstone.core.service.impl.SecurityServiceImpl.getClaims;
import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private CourseRepository mockCourseRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private ModelMapper mockModelMapper;

    private UserServiceImpl userServiceImplUnderTest;
    private String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImM5YWZkYTM2ODJlYmYwOWViMzA1NWMxYzRiZDM5Yjc1MWZiZjgxOTUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1OTEzMjY2Mjk1MzctNHAxbmhlNGk2amw3dTRqYzJscGdiMTVzb2MybWt0Z28uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1OTEzMjY2Mjk1MzctNHAxbmhlNGk2amw3dTRqYzJscGdiMTVzb2MybWt0Z28uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTY4MDgyODIyMDE1MDUyMTAyOTIiLCJoZCI6ImVkdS5hdWEuYW0iLCJlbWFpbCI6Im1hcmlhX3NhbG1hc2lhbkBlZHUuYXVhLmFtIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJPUkZmbXlseFdRWGFMZTlKZUlldE9nIiwibmFtZSI6Ik1hcmlhIFNhbG1hc2lhbiIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BR05teXhaTFI3b1c2X2o2R2hTSF9IQTlwOUNya0dodlFZMlBqR25kT09HVT1zOTYtYyIsImdpdmVuX25hbWUiOiJNYXJpYSIsImZhbWlseV9uYW1lIjoiU2FsbWFzaWFuIiwibG9jYWxlIjoiZW4iLCJpYXQiOjE2ODMyMjYwMTAsImV4cCI6MTc0NjYzMjM2OH0.BRpzcoJUYB21QiFfQqiPEP4q7uAAdAOPCHHB9UsG6FMurWG1ki2IhJedHkX2aW8TBENryTm8Y09hY7rK9VjJGqQrDYGVJ0_5ymcLk1jazi1q0FEH3owRDg8JD6UIHrUfhCms-W2sayOstut0m-jIj2BGjFxA8k5oH0r-5jAf_dh8F_FdDpPqbKw9uyuhWlXUoGOgS_84sWIhts_vaqTjFcCmaKjqq-savy8Vg0bjT4Ln2kw1ht8bBjlwYPnD7cNx9vj62udK9dIPveac7y6NQJAW0qUoCsM7ABxCflkc4rYerxoSZOZG1Z7XeswSPVXKiFcCiZO_6NxMVkB7RbzBiQ";


    @BeforeEach
    void setUp() {
        userServiceImplUnderTest = new UserServiceImpl(mockUserRepository, mockCourseRepository, mockRoleRepository,
                mockModelMapper);
    }

    @Test
    void testAddCourseToUser() {
        final UserCourseModel model = new UserCourseModel();
        model.setUserId(0L);
        model.setCourseId(0L);

        final UserModel expectedResult = new UserModel();
        expectedResult.setId(0L);
        expectedResult.setUsername("username");
        expectedResult.setName("name");
        expectedResult.setLastName("lastName");
        expectedResult.setEnabled(false);

        final Optional<User> user = Optional.of(User.builder()
                .id(0L)
                .username("username")
                .name("name")
                .lastName("lastName")
                .enabled(false)
                .deleted(false)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .courses(new HashSet<>())
                .build());
        when(mockUserRepository.findById(0L)).thenReturn(user);

        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        course1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Course> course = Optional.of(course1);
        when(mockCourseRepository.findByIdAndIsDeletedFalse(0L)).thenReturn(course);

        Set<Course> courses = new HashSet<>();
        courses.add(course1);
        final UserModel userModel = new UserModel();
        userModel.setId(0L);
        userModel.setUsername("username");
        userModel.setName("name");
        userModel.setLastName("lastName");
        userModel.setEnabled(false);
        when(mockModelMapper.map(User.builder()
                .id(0L)
                .username("username")
                .name("name")
                .lastName("lastName")
                .enabled(false)
                .deleted(false)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .courses(courses)
                .build(), UserModel.class)).thenReturn(userModel);


        final UserModel result = userServiceImplUnderTest.addCourseToUser(model);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testAddCourseToUser_UserRepositoryReturnsAbsent() {
        final UserCourseModel model = new UserCourseModel();
        model.setUserId(0L);
        model.setCourseId(0L);

        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplUnderTest.addCourseToUser(model)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testAddCourseToUser_CourseRepositoryReturnsAbsent() {
        final UserCourseModel model = new UserCourseModel();
        model.setUserId(0L);
        model.setCourseId(0L);

        final Optional<User> user = Optional.of(User.builder()
                .id(0L)
                .username("username")
                .name("name")
                .lastName("lastName")
                .enabled(false)
                .deleted(false)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .build());
        when(mockUserRepository.findById(0L)).thenReturn(user);

        when(mockCourseRepository.findByIdAndIsDeletedFalse(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userServiceImplUnderTest.addCourseToUser(model)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testAddCourseToUser_ModelMapperThrowsConfigurationException() {
        final UserCourseModel model = new UserCourseModel();
        model.setUserId(0L);
        model.setCourseId(0L);

        final Optional<User> user = Optional.of(User.builder()
                .id(0L)
                .username("username")
                .name("name")
                .lastName("lastName")
                .enabled(false)
                .deleted(false)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .courses(new HashSet<>())
                .build());
        when(mockUserRepository.findById(0L)).thenReturn(user);

        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        course1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Course> course = Optional.of(course1);
        when(mockCourseRepository.findByIdAndIsDeletedFalse(0L)).thenReturn(course);
        Set<Course> courses = new HashSet<>();
        courses.add(course1);
        when(mockModelMapper.map(User.builder()
                .id(0L)
                .username("username")
                .name("name")
                .lastName("lastName")
                .enabled(false)
                .deleted(false)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .courses(courses)
                .build(), UserModel.class)).thenThrow(ConfigurationException.class);

        assertThatThrownBy(() -> userServiceImplUnderTest.addCourseToUser(model))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testAddCourseToUser_ModelMapperThrowsMappingException() {
        final UserCourseModel model = new UserCourseModel();
        model.setUserId(0L);
        model.setCourseId(0L);

        final Optional<User> user = Optional.of(User.builder()
                .id(0L)
                .username("username")
                .name("name")
                .lastName("lastName")
                .enabled(false)
                .deleted(false)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .courses(new HashSet<>())
                .build());
        when(mockUserRepository.findById(0L)).thenReturn(user);

        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        course1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Course> course = Optional.of(course1);
        when(mockCourseRepository.findByIdAndIsDeletedFalse(0L)).thenReturn(course);
        Set<Course> courses = new HashSet<>();
        courses.add(course1);

        when(mockModelMapper.map(User.builder()
                .id(0L)
                .username("username")
                .name("name")
                .lastName("lastName")
                .enabled(false)
                .deleted(false)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .courses(courses)
                .build(), UserModel.class)).thenThrow(MappingException.class);

        assertThatThrownBy(() -> userServiceImplUnderTest.addCourseToUser(model)).isInstanceOf(MappingException.class);
    }

    @Test
    void testDelete() {

        final Optional<User> user = Optional.of(User.builder()
                .id(0L)
                .username("username")
                .name("name")
                .lastName("lastName")
                .enabled(false)
                .deleted(false)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .courses(new HashSet<>())
                .build());
        when(mockUserRepository.findById(0L)).thenReturn(user);

        userServiceImplUnderTest.delete(0L);


        verify(mockUserRepository).save(User.builder()
                .id(0L)
                .username("username")
                .name("name")
                .lastName("lastName")
                .enabled(false)
                .deleted(true)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .courses(new HashSet<>())
                .build());
    }

    @Test
    void testDelete_UserRepositoryFindByIdReturnsAbsent() {
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userServiceImplUnderTest.delete(0L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testDelete_UserRepositorySaveThrowsOptimisticLockingFailureException() {

        final Optional<User> user = Optional.of(User.builder()
                .id(0L)
                .username("username")
                .name("name")
                .lastName("lastName")
                .enabled(false)
                .deleted(false)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .build());
        when(mockUserRepository.findById(0L)).thenReturn(user);

        when(mockUserRepository.save(User.builder()
                .id(0L)
                .username("username")
                .name("name")
                .lastName("lastName")
                .enabled(false)
                .deleted(true)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .build())).thenThrow(OptimisticLockingFailureException.class);

        assertThatThrownBy(() -> userServiceImplUnderTest.delete(0L))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testSaveUser() {
        final CreateUserEvent event = new CreateUserEvent(getClaims(token));

        final Optional<User> user = Optional.of(User.builder()
                .id(null)
                .username("maria_salmasian@edu.aua.am")
                .name("Maria")
                .lastName("Salmasian")
                .enabled(false)
                .deleted(false)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .courses(new HashSet<>())
                .build());
        when(mockUserRepository.findUserByUsername("maria_salmasian@edu.aua.am")).thenReturn(user);

        userServiceImplUnderTest.saveUser(event);

        verify(mockUserRepository).save(User.builder()
                .id(null)
                .username("maria_salmasian@edu.aua.am")
                .name("Maria")
                .lastName("Salmasian")
                .enabled(false)
                .deleted(false)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .updatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .role(new Role())
                .courses(new HashSet<>())
                .build());
    }

    @Test
    void testSaveUser_UserRepositoryFindUserByUsernameReturnsAbsent() {

        final CreateUserEvent event = new CreateUserEvent(getClaims(token));
        when(mockUserRepository.findUserByUsername("maria_salmasian@edu.aua.am")).thenReturn(Optional.empty());


        when(mockRoleRepository.getRoleByRoleName(RoleName.STUDENT)).thenReturn(new Role());

        userServiceImplUnderTest.saveUser(event);

        verify(mockUserRepository).save(ArgumentMatchers.any(User.class));
    }

}
