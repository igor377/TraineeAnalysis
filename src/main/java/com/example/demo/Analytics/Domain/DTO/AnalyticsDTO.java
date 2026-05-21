package com.example.demo.Analytics.Domain.DTO;

import com.example.demo.Analytics.Domain.Enum.Departament;
import com.example.demo.Analytics.Domain.Enum.GenderEnum;
import com.example.demo.Analytics.Domain.Enum.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AnalyticsDTO(
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
        String expectations,

        @NotNull
        StatusEnum situation
) {
}
