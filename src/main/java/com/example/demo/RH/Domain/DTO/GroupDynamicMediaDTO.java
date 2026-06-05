package com.example.demo.RH.Domain.DTO;

import com.example.demo.Analytics.Domain.Enum.Departament;
import com.example.demo.Analytics.Domain.Enum.StatusEnum;

public record GroupDynamicMediaDTO (
        Departament departament,
        StatusEnum status,
        Double teamwork,
        Double problemSolving) {
}
