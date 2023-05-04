package com.example.capstone.infrastucture.repository;

import com.example.capstone.infrastucture.entity.Role;
import com.example.capstone.utils.enumeration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByRoleName(RoleName role);
}
