package com.example.demo.Gemini.Controller;

import com.example.demo.Gemini.Domain.DTO.AnalisesTraineeDTO;
import com.example.demo.Gemini.Domain.Entity.GeminiAnalytics;
import com.example.demo.Gemini.Service.AnalisesTraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
