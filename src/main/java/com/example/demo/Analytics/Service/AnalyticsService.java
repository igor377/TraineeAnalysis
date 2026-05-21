package com.example.demo.Analytics.Service;

import com.example.demo.Analytics.Domain.DTO.AnalyticsDTO;
import com.example.demo.Analytics.Domain.DTO.AnalyticsResponseDTO;
import com.example.demo.Analytics.Domain.Entity.Analytics;
import com.example.demo.Analytics.Repository.AnalyticsRepository;
import com.example.demo.exceptions.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    public AnalyticsResponseDTO createAnalytics(AnalyticsDTO analyticsDTO) {
        Analytics analytics = analyticsRepository.findByEmail(analyticsDTO.email()).orElse(null);
        if (analytics != null) {
            throw new ConflictException("Email already in use");
        }

        Analytics analytics1 = analyticsRepository.findByRegistration(analyticsDTO.registration()).orElse(null);
        if (analytics1 != null) {
            throw new ConflictException("Registration already in use");
        }

        Analytics data = new Analytics();

        data.setEmail(analyticsDTO.email());
        data.setRegistration(analyticsDTO.registration());
        data.setName(analyticsDTO.name());
        data.setBirthDate(analyticsDTO.birthDate());
        data.setMajor(analyticsDTO.major());
        data.setGender(analyticsDTO.gender());
        data.setDepartament(analyticsDTO.departament());
        data.setExpectations(analyticsDTO.expectations());
        data.setStatus(analyticsDTO.situation());
        analyticsRepository.save(data);
        return new AnalyticsResponseDTO(data);
    }
}
