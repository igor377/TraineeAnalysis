package com.example.demo.Analytics.Controller;

import com.example.demo.Analytics.Domain.DTO.*;
import com.example.demo.Analytics.Domain.Entity.Analytics;
import com.example.demo.Analytics.Repository.AnalyticsRepository;
import com.example.demo.Analytics.Service.AnalyticsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private AnalyticsRepository analyticsRepository;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnalyticsResponseDTO> create(@RequestBody @Valid AnalyticsDTO analyticsDTO) {
        AnalyticsResponseDTO analytics = analyticsService.createAnalytics(analyticsDTO);
        return ResponseEntity.ok(analytics);
    }

    @PatchMapping("/group-dynamic/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateGroupDynamic(@PathVariable("id") UUID id, @RequestBody @Valid GroupDynamicDTO data) {
        analyticsService.groupDynamic(id, data);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/individual-interview/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateIndividualInterview(@PathVariable("id") UUID id, @RequestBody @Valid IndividualInterviewDTO data) {
        analyticsService.individualInterview(id, data);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/trainee-feedback/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateTraineeFeedback(@PathVariable("id") UUID id, @RequestBody @Valid TraineeFeedbackDTO data) {
        analyticsService.traineeFeedback(id, data);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/final-evaluation/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateFinalClassification(@PathVariable("id") UUID id, @RequestBody @Valid FinalClassificationDTO data) {
        analyticsService.finalClassification(id, data);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Page<AnalyticsResponseDTO>> findAll(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<AnalyticsResponseDTO> analyticsPage = analyticsService.findAll(pageable);
        return ResponseEntity.ok(analyticsPage);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Analytics>> createMultiple(@RequestBody List<Analytics> analyticsList) {
        List<Analytics> savedAnalytics = analyticsRepository.saveAll(analyticsList);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnalytics);
    }
}
