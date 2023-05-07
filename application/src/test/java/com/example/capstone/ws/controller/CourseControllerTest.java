package com.example.capstone.ws.controller;

import com.example.capstone.core.model.ClusterModel;
import com.example.capstone.core.model.CourseModel;
import com.example.capstone.core.service.AttentionService;
import com.example.capstone.core.service.CourseService;
import com.example.capstone.ws.dto.AverageAttentionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService mockCourseService;

    @MockBean
    private AttentionService attentionService;

    private String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImM5YWZkYTM2ODJlYmYwOWViMzA1NWMxYzRiZDM5Yjc1MWZiZjgxOTUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1OTEzMjY2Mjk1MzctNHAxbmhlNGk2amw3dTRqYzJscGdiMTVzb2MybWt0Z28uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1OTEzMjY2Mjk1MzctNHAxbmhlNGk2amw3dTRqYzJscGdiMTVzb2MybWt0Z28uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTY4MDgyODIyMDE1MDUyMTAyOTIiLCJoZCI6ImVkdS5hdWEuYW0iLCJlbWFpbCI6Im1hcmlhX3NhbG1hc2lhbkBlZHUuYXVhLmFtIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJPUkZmbXlseFdRWGFMZTlKZUlldE9nIiwibmFtZSI6Ik1hcmlhIFNhbG1hc2lhbiIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BR05teXhaTFI3b1c2X2o2R2hTSF9IQTlwOUNya0dodlFZMlBqR25kT09HVT1zOTYtYyIsImdpdmVuX25hbWUiOiJNYXJpYSIsImZhbWlseV9uYW1lIjoiU2FsbWFzaWFuIiwibG9jYWxlIjoiZW4iLCJpYXQiOjE2ODMyMjYwMTAsImV4cCI6MTc0NjYzMjM2OH0.BRpzcoJUYB21QiFfQqiPEP4q7uAAdAOPCHHB9UsG6FMurWG1ki2IhJedHkX2aW8TBENryTm8Y09hY7rK9VjJGqQrDYGVJ0_5ymcLk1jazi1q0FEH3owRDg8JD6UIHrUfhCms-W2sayOstut0m-jIj2BGjFxA8k5oH0r-5jAf_dh8F_FdDpPqbKw9uyuhWlXUoGOgS_84sWIhts_vaqTjFcCmaKjqq-savy8Vg0bjT4Ln2kw1ht8bBjlwYPnD7cNx9vj62udK9dIPveac7y6NQJAW0qUoCsM7ABxCflkc4rYerxoSZOZG1Z7XeswSPVXKiFcCiZO_6NxMVkB7RbzBiQ";


    @Test
    void testCreateCourse() throws Exception {

        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        clusterModel.setId(0);
        clusterModel.setName("cluster");
        courseModel.setClusters(List.of(clusterModel));
        when(mockCourseService.createCourse(any(CourseModel.class))).thenReturn(courseModel);

        final MockHttpServletResponse response = mockMvc.perform(post("/course")
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(courseModel)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(courseModel));
    }

    @Test
    void testDeleteCourse() throws Exception {

        final MockHttpServletResponse response = mockMvc.perform(delete("/course/{id}", 0)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(mockCourseService).deleteCourse(0L);
    }

    @Test
    void testGetCourse() throws Exception {

        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));
        when(mockCourseService.getCourse(0L)).thenReturn(courseModel);


        final MockHttpServletResponse response = mockMvc.perform(get("/course/{id}", 0)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(courseModel));
    }

    @Test
    void testGetCourses() throws Exception {

        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));
        final List<CourseModel> courseModels = List.of(courseModel);
        when(mockCourseService.getCourses(any(Pageable.class))).thenReturn(courseModels);

        final MockHttpServletResponse response = mockMvc.perform(get("/course")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(courseModels));
    }

    @Test
    void testGetCourses_CourseServiceReturnsNoItems() throws Exception {
        when(mockCourseService.getCourses(any(Pageable.class))).thenReturn(Collections.emptyList());

        final MockHttpServletResponse response = mockMvc.perform(get("/course")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

//    @Test
//    void testGetAverageByCourse() throws Exception {
//        when(mockAttentionService.getAverageByCourseAndDate(0L, LocalDateTime.of(2020, 1, 1, 0, 0, 0)))
//                .thenReturn(AverageAttentionDto.builder().build());
//        when(LocalDateTime.parse(any(String.class), DateTimeFormatter.ISO_LOCAL_DATE_TIME)).thenReturn(LocalDateTime.now());
//
//        final MockHttpServletResponse response = mockMvc.perform(get("/course/{courseId}/attention-average", 0)
//                        .header("Authorization", token)
//                        .param("date", "2021-10-11")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(AverageAttentionDto.builder().build()));
//    }

    @Test
    void testGetCoursesByUserIDAndCluster() throws Exception {

        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));
        final List<CourseModel> courseModels = List.of(courseModel);
        when(mockCourseService.getCoursesByUserAndClusterName(any(Pageable.class), eq(0L),
                eq("clusterName"))).thenReturn(courseModels);

        final MockHttpServletResponse response = mockMvc.perform(
                        get("/course/users/{userId}/cluster/{clusterName}", 0, "clusterName")
                                .header("Authorization", token)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(courseModels));
    }

    @Test
    void testGetCoursesByUserIDAndCluster_CourseServiceReturnsNoItems() throws Exception {
        when(mockCourseService.getCoursesByUserAndClusterName(any(Pageable.class), eq(0L),
                eq("clusterName"))).thenReturn(Collections.emptyList());

        final MockHttpServletResponse response = mockMvc.perform(
                        get("/course/users/{userId}/cluster/{clusterName}", 0, "clusterName")
                                .header("Authorization", token)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
