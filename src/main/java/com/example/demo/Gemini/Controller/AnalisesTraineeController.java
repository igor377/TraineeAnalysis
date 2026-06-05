package com.example.demo.Gemini.Controller;

import com.example.demo.Gemini.Domain.DTO.AnalisesTraineeDTO;
import com.example.demo.Gemini.Domain.DTO.ProcessAnalyticsDTO;
import com.example.demo.Gemini.Domain.Entity.GeminiAnalytics;
import com.example.demo.Gemini.Domain.Entity.ProcessAnalytics;
import com.example.demo.Gemini.Service.AnalisesTraineeService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/AnalisesTrainee")
public class AnalisesTraineeController {

    @Autowired
    private AnalisesTraineeService analisesTraineeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<AnalisesTraineeDTO>> getAllAnalises() {
        List<AnalisesTraineeDTO> analises = analisesTraineeService.getAllAnalises();
        return ResponseEntity.ok(analises);
    }

    @PostMapping("/processar/{semestre}")
    public ResponseEntity<ProcessAnalyticsDTO> processAllTrainees(@PathVariable String semestre) {
        analisesTraineeService.processPrompts(semestre);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/processar/analise")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ProcessAnalyticsDTO> createJob() {
        ProcessAnalyticsDTO jobDto = analisesTraineeService.newJob();

        analisesTraineeService.executeJob(jobDto.id());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jobDto);
    }

    @GetMapping("/analises/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ProcessAnalyticsDTO> findById(@PathVariable UUID id) {
        ProcessAnalyticsDTO processAnalytics = analisesTraineeService.findById(id);
        return ResponseEntity.ok(processAnalytics);
    }


}
