package com.example.demo.Gemini.Repository;

import com.example.demo.Gemini.Domain.Entity.GeminiAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnalisesTraineeRepository extends JpaRepository<GeminiAnalytics, UUID> {
}
