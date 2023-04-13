package com.example.capstone.infrasturcture.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "attention", schema = "face_recognition")
public class Attention {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    private User user;
    @Column(name = "angle")
    private float angle;
}
