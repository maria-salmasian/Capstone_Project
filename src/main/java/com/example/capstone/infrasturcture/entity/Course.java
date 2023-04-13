package com.example.capstone.infrasturcture.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "course", schema = "face_recognition")
public class Course {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 50, message = "title too long")
    @Column(name = "title")
    private String title;

    @Size(max = 1000, message = "title too long")
    @Column(name = "description")
    private String description;

    @Column(name = "cluster")
    private Integer cluster;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    private User user;

}
