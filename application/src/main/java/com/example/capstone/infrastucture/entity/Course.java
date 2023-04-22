package com.example.capstone.infrastucture.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "course", schema = "face_recognition")
@EqualsAndHashCode(of = {"id","title"})
public class Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50, message = "title too long")
    @Column(name = "title")
    private String title;

    @Size(max = 1000, message = "title too long")
    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinTable(schema = "face_recognition",
            name = "link_course_cluster",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "cluster_id"))
    private Set<Cluster> clusters = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    private Set<User> users = new HashSet<>();
}
