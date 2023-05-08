package com.example.capstone.ws.controller;


import com.example.capstone.core.model.ClusterModel;
import com.example.capstone.core.service.ClusterService;
import com.example.capstone.utils.annotation.IsAuthenticated;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cluster")
@RequiredArgsConstructor
@Validated
public class ClusterController {

    private final ClusterService clusterService;

    @GetMapping()
    public ResponseEntity<List<String>> getClusters(@IsAuthenticated @Valid @RequestHeader("Authorization") String token, Pageable pageable) {
        return ResponseEntity.ok().body(clusterService.getClusters(pageable));
    }


}
