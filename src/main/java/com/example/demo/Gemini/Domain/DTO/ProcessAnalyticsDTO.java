package com.example.demo.Gemini.Domain.DTO;

import com.example.demo.Gemini.Domain.Entity.ProcessAnalytics;
import com.example.demo.Gemini.Domain.Enum.JobStatusEnum;

import java.util.UUID;

public record ProcessAnalyticsDTO(
        UUID id,
        JobStatusEnum status,
        String analysisResult
) {

    public ProcessAnalyticsDTO(ProcessAnalytics processAnalytics) {
        this(processAnalytics.getId(), processAnalytics.getJobStatus(), processAnalytics.getAnalysis());
    }
}
