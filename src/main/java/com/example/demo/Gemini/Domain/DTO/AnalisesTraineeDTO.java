package com.example.demo.Gemini.Domain.DTO;

import com.example.demo.Gemini.Domain.Entity.GeminiAnalytics;
import java.time.LocalDateTime;
import java.util.UUID;

public record AnalisesTraineeDTO(
        UUID id,
        String email,
        String name,
        String registration,
        String major,
        String avaliacao,
        LocalDateTime dataAnalysis
) {
    public AnalisesTraineeDTO(GeminiAnalytics entity) {
        this(
                entity.getId(),
                entity.getTraining() != null ? entity.getTraining().getEmail() : null,
                entity.getTraining() != null ? entity.getTraining().getName() : null,
                entity.getTraining() != null ? entity.getTraining().getRegistration() : null,
                entity.getTraining() != null ? entity.getTraining().getMajor() : null,
                entity.getAvaliacao(),
                entity.getDataAnalysis()
        );
    }
}