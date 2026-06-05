package com.example.demo.Analytics.Domain.DTO;

import com.example.demo.Analytics.Domain.Enum.Classification;
import com.example.demo.Analytics.Domain.Enum.DifficultyLevel;

public record TraineeFeedbackDTO(
        Classification satisfactionLevel,
        DifficultyLevel perceivedDifficulty,
        String candidateComments
) {
}
