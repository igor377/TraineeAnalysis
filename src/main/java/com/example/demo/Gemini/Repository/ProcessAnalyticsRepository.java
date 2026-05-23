package com.example.demo.Gemini.Repository;

import com.example.demo.Gemini.Domain.Entity.GeminiAnalytics;
import com.example.demo.Gemini.Domain.Entity.ProcessAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.stream.Stream;

public interface ProcessAnalyticsRepository extends JpaRepository<ProcessAnalytics, UUID> {

}
