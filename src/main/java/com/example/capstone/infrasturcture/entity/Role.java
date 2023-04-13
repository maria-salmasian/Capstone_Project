package com.example.capstone.infrasturcture.entity;

import com.example.capstone.utils.enumeration.RoleName;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles", schema = "face_recognition")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "roleName")
    private RoleName roleName;

}


