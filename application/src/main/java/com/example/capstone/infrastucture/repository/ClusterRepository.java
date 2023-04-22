package com.example.capstone.infrastucture.repository;

import com.example.capstone.infrastucture.entity.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {

    Collection<Cluster> findAllByIdIn(List<Long> ids);
}
