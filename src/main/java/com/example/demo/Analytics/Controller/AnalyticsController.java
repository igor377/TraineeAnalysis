package com.example.demo.Analytics.Controller;

import com.example.demo.Analytics.Domain.DTO.AnalyticsDTO;
import com.example.demo.Analytics.Domain.DTO.AnalyticsResponseDTO;
import com.example.demo.Analytics.Service.AnalyticsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnalyticsResponseDTO> create(@RequestBody @Valid AnalyticsDTO analyticsDTO) {
        AnalyticsResponseDTO analytics = analyticsService.createAnalytics(analyticsDTO);
        return ResponseEntity.ok(analytics);
    }
}
