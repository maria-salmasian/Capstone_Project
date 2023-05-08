package com.example.capstone.core.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClusterService {
    List<String> getClusters(Pageable pageable);

}
