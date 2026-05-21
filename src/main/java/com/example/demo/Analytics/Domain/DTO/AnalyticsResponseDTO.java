package com.example.demo.Analytics.Domain.DTO;

import com.example.demo.Analytics.Domain.Entity.Analytics;
import com.example.demo.Analytics.Domain.Enum.Departament;
import com.example.demo.Analytics.Domain.Enum.GenderEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AnalyticsResponseDTO(
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

        @NotBlank
        String expectations
) {

    public AnalyticsResponseDTO(Analytics analytics) {
        this(
                analytics.getEmail(),
                analytics.getName(),
                analytics.getRegistration(),
                analytics.getBirthDate(),
                analytics.getMajor(),
                analytics.getGender(),
                analytics.getDepartament(),
                analytics.getExpectations()
        );
    }
}
