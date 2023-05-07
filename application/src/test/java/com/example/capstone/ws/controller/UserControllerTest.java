package com.example.capstone.ws.controller;

import com.example.capstone.config.ClientConfig;
import com.example.capstone.core.model.UserCourseModel;
import com.example.capstone.core.model.UserModel;
import com.example.capstone.core.service.AttentionService;
import com.example.capstone.core.service.SecurityService;
import com.example.capstone.core.service.UserService;
import com.example.capstone.ws.dto.AverageAttentionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;
    @MockBean
    private SecurityService mockSecurityService;
    @MockBean
    private ClientConfig mockClientConfig;

    @MockBean
    private AttentionService attentionService;
    @Autowired
    private ObjectMapper objectMapper;
    private String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImM5YWZkYTM2ODJlYmYwOWViMzA1NWMxYzRiZDM5Yjc1MWZiZjgxOTUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1OTEzMjY2Mjk1MzctNHAxbmhlNGk2amw3dTRqYzJscGdiMTVzb2MybWt0Z28uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1OTEzMjY2Mjk1MzctNHAxbmhlNGk2amw3dTRqYzJscGdiMTVzb2MybWt0Z28uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTY4MDgyODIyMDE1MDUyMTAyOTIiLCJoZCI6ImVkdS5hdWEuYW0iLCJlbWFpbCI6Im1hcmlhX3NhbG1hc2lhbkBlZHUuYXVhLmFtIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJPUkZmbXlseFdRWGFMZTlKZUlldE9nIiwibmFtZSI6Ik1hcmlhIFNhbG1hc2lhbiIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BR05teXhaTFI3b1c2X2o2R2hTSF9IQTlwOUNya0dodlFZMlBqR25kT09HVT1zOTYtYyIsImdpdmVuX25hbWUiOiJNYXJpYSIsImZhbWlseV9uYW1lIjoiU2FsbWFzaWFuIiwibG9jYWxlIjoiZW4iLCJpYXQiOjE2ODMyMjYwMTAsImV4cCI6MTc0NjYzMjM2OH0.BRpzcoJUYB21QiFfQqiPEP4q7uAAdAOPCHHB9UsG6FMurWG1ki2IhJedHkX2aW8TBENryTm8Y09hY7rK9VjJGqQrDYGVJ0_5ymcLk1jazi1q0FEH3owRDg8JD6UIHrUfhCms-W2sayOstut0m-jIj2BGjFxA8k5oH0r-5jAf_dh8F_FdDpPqbKw9uyuhWlXUoGOgS_84sWIhts_vaqTjFcCmaKjqq-savy8Vg0bjT4Ln2kw1ht8bBjlwYPnD7cNx9vj62udK9dIPveac7y6NQJAW0qUoCsM7ABxCflkc4rYerxoSZOZG1Z7XeswSPVXKiFcCiZO_6NxMVkB7RbzBiQ";


    @InjectMocks
    private UserController userController;

    @Test
    void testAddCourseToUser() throws Exception {

        final UserModel userModel = new UserModel();
        userModel.setId(0L);
        userModel.setUsername("username");
        userModel.setName("name");
        userModel.setLastName("lastName");
        userModel.setEnabled(false);
        final UserCourseModel userCourseModel = new UserCourseModel();
        userCourseModel.setUserId(0L);
        userCourseModel.setCourseId(0L);
        when(mockUserService.addCourseToUser(userCourseModel)).thenReturn(userModel);

        final MockHttpServletResponse response = mockMvc.perform(post("/user/courses")
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(userCourseModel)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(userModel));
    }

//    @Test
//    void testGetAverageByUserAndCourse() throws Exception {
//        when(mockAttentionService.getAverageByUserAndCourseAndDate(0L, 0L,
//                LocalDateTime.of(2020, 1, 1, 0, 0, 0))).thenReturn(AverageAttentionDto.builder().build());
//
//        final MockHttpServletResponse response = mockMvc.perform(
//                        get("/user/{userId}/courses/{courseId}/attention-average", 0, 0)
//                                .header("Authorization", token)
//                                .param("date", "2020-01-01")
//                                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(AverageAttentionDto.builder().build()));
//    }

    @Test
    void testLogin() throws Exception {
        when(mockSecurityService.exchangeAuthorizationCode("code")).thenReturn(token);

        final MockHttpServletResponse response = mockMvc.perform(get("/user/login")
                        .param("code", "code")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(token);
    }

    @Test
    void testLogout() throws Exception {
        when(mockClientConfig.getLogoutUrl()).thenReturn("result");

        final MockHttpServletResponse response = mockMvc.perform(get("/user/logout")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.TEMPORARY_REDIRECT.value());
    }

    @Test
    void testDelete() throws Exception {

        final MockHttpServletResponse response = mockMvc.perform(delete("/user/delete/{userId}", 0)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(mockUserService).delete(0L);
    }
}
