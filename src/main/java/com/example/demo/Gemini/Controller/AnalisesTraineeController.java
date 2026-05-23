package com.example.demo.Gemini.Controller;

import com.example.demo.Gemini.Domain.DTO.AnalisesTraineeDTO;
import com.example.demo.Gemini.Domain.DTO.ProcessAnalyticsDTO;
import com.example.demo.Gemini.Domain.Entity.GeminiAnalytics;
import com.example.demo.Gemini.Domain.Entity.ProcessAnalytics;
import com.example.demo.Gemini.Service.AnalisesTraineeService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/AnalisesTrainee")
public class AnalisesTraineeController {

    @Autowired
    private AnalisesTraineeService analisesTraineeService;

    @GetMapping
    public ResponseEntity<List<AnalisesTraineeDTO>> getAllAnalises() {
        List<AnalisesTraineeDTO> analises = analisesTraineeService.getAllAnalises();
        return ResponseEntity.ok(analises);
    }

    @GetMapping("/processar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> processPrompts() {
        analisesTraineeService.processPrompts();
        return ResponseEntity.accepted().body("Processamento de prompts iniciado em segundo plano.");
    }

    @GetMapping("/processar/analise")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ProcessAnalyticsDTO> createJob() {
        ProcessAnalyticsDTO processAnalytics = analisesTraineeService.newJob();
        return ResponseEntity.ok(processAnalytics);
    }

    @GetMapping("/analises/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ProcessAnalyticsDTO> findById(@PathVariable UUID id) {
        ProcessAnalyticsDTO processAnalytics = analisesTraineeService.findById(id);
        return ResponseEntity.ok(processAnalytics);
    }


}
