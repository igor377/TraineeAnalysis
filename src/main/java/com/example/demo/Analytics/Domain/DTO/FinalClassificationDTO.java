package com.example.demo.Analytics.Domain.DTO;

import com.example.demo.Analytics.Domain.Enum.StatusEnum;

public record FinalClassificationDTO(
        StatusEnum status,
        String finalObservations
) {
}
