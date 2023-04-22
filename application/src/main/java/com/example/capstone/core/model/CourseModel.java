package com.example.capstone.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class CourseModel {

    private Integer id;
    private String title;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> clusterIds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ClusterModel> clusters;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private boolean isDeleted;
    private List<UserModel> users;
}
