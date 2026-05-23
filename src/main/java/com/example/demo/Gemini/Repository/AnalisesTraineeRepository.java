package com.example.demo.Gemini.Repository;

import com.example.demo.Analytics.Domain.Enum.StatusEnum;
import com.example.demo.Gemini.Domain.Entity.GeminiAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.stream.Stream;

public interface AnalisesTraineeRepository extends JpaRepository<GeminiAnalytics, UUID> {
    Stream<GeminiAnalytics> findTop100ByTrainingStatusOrderByDataAnalysisDesc(StatusEnum status);
}
