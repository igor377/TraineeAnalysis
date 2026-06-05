package com.example.demo.RH.Domain.DTO;

import com.example.demo.Analytics.Domain.Enum.Departament;
import com.example.demo.Analytics.Domain.Enum.StatusEnum;

public record IndividualInterviewMediaDTO(
        Departament departament,
        StatusEnum status,
        Double culturalFit,
        Double communicationSkills
) {
}
