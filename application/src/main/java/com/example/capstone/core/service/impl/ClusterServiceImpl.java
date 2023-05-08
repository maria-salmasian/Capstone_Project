package com.example.capstone.core.service.impl;

import com.example.capstone.core.model.ClusterModel;
import com.example.capstone.core.service.ClusterService;
import com.example.capstone.infrastucture.repository.ClusterRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClusterServiceImpl implements ClusterService {

    private final ClusterRepository clusterRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<String> getClusters(Pageable pageable) {
        return clusterRepository.findAll().stream()

                .map(c -> modelMapper.map(c, ClusterModel.class))
                .map(ClusterModel::getName)
                .distinct()
                .toList();
    }
}
