package com.example.demo.Analytics.Domain.DTO;

import com.example.demo.Analytics.Domain.Entity.Analytics;
import com.example.demo.Analytics.Domain.Enum.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.api.client.json.Json;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDate;
import java.util.UUID;

public record AnalyticsResponseDTO(
        @NotBlank
        UUID id,

        @NotBlank
        String email,

        @NotBlank
        String name,

        @NotBlank
        String registration,

        @NotNull
        LocalDate birthDate,

        @NotBlank
        String major,

        @NotNull
        GenderEnum gender,

        @NotNull
        Departament departament,

        //  Group Dynamic

        @JsonFormat(shape = JsonFormat.Shape.OBJECT)
        Classification teamwork,

        @JsonFormat(shape = JsonFormat.Shape.OBJECT)
        Classification problemSolving,

        String groupDynamicObservations,

        // Individual Interview

        @JsonFormat(shape = JsonFormat.Shape.OBJECT)
        Classification culturalFit,

        @JsonFormat(shape = JsonFormat.Shape.OBJECT)
        Classification communicationSkills,

        String historyMotivation,

        // Trainee Feedback

        @JsonFormat(shape = JsonFormat.Shape.OBJECT)
        Classification satisfactionLevel,

        @JsonFormat(shape = JsonFormat.Shape.OBJECT)
        DifficultyLevel perceivedDifficulty,

        String candidateComments,

        // Final Classification

        StatusEnum status,

        String finalObservations
) {

    public AnalyticsResponseDTO (Analytics analytics) {
        this(
                analytics.getId(),
                analytics.getEmail(),
                analytics.getName(),
                analytics.getRegistration(),
                analytics.getBirthDate(),
                analytics.getMajor(),
                analytics.getGender(),
                analytics.getDepartament(),
                Classification.fromValue(analytics.getTeamwork()),
                Classification.fromValue(analytics.getProblemSolving()),
                analytics.getGroupDynamicObservations(),
                Classification.fromValue(analytics.getCulturalFit()),
                Classification.fromValue(analytics.getCommunicationSkills()),
                analytics.getHistoryMotivation(),
                Classification.fromValue(analytics.getSatisfactionLevel()),
                DifficultyLevel.fromValue(analytics.getPerceivedDifficulty()),
                analytics.getCandidateComments(),
                analytics.getStatus(),
                analytics.getFinalObservations()
        );
    }
}

