package com.example.demo.Analytics.Domain.DTO;

import com.example.demo.Analytics.Domain.Enum.Classification;

public record GroupDynamicDTO (
        Classification teamwork,
        Classification problemSolving,
        String groupDynamicObservations
){
}
