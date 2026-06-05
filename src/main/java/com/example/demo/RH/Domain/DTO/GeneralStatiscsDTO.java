package com.example.demo.RH.Domain.DTO;

import com.example.demo.Analytics.Domain.Enum.Departament;
import com.example.demo.Analytics.Domain.Enum.StatusEnum;

public record GeneralStatiscsDTO(
        Departament departament,
        StatusEnum status,
        Double culturalFit,
        Double communicationSkills,
        Double teamwork,
        Double problemSolving,
        Double satisfactionLevel,
        Double perceivedDifficulty
) {}