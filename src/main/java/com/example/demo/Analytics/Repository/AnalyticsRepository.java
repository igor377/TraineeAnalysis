package com.example.demo.Analytics.Repository;

import com.example.demo.Analytics.Domain.Entity.Analytics;
import com.example.demo.Analytics.Domain.Enum.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnalyticsRepository extends JpaRepository<Analytics, UUID> {
    Optional<Analytics>findByEmail(String email);
    Optional<Analytics>findByRegistration(String registration);
    Page<Analytics> findByStatusIn(List<String> statusLista, Pageable pageable);
}
