package com.example.demo.Analytics.Domain.DTO;

import com.example.demo.Analytics.Domain.Enum.Classification;

public record IndividualInterviewDTO(
        Classification culturalFit,
        Classification communicationSkills,
        String historyMotivation
) {
}
