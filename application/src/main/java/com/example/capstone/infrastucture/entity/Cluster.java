package com.example.capstone.infrastucture.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "cluster", schema = "face_recognition")
@EqualsAndHashCode(of = {"id", "name"})
public class Cluster {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "clusters", cascade = CascadeType.MERGE)
    private Set<Course> courses = new HashSet<>();
}
