package com.example.demo.Analytics.Service;

import com.example.demo.Analytics.Domain.DTO.*;
import com.example.demo.Analytics.Domain.Entity.Analytics;
import com.example.demo.Analytics.Repository.AnalyticsRepository;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    public AnalyticsResponseDTO createAnalytics(AnalyticsDTO analyticsDTO) {
        if (analyticsRepository.existsByEmail(analyticsDTO.email())) {
            throw new ConflictException("Email already exists: " + analyticsDTO.email());
        }

        if (analyticsRepository.existsByRegistration(analyticsDTO.registration())) {
            throw new ConflictException("Registration already exists: " + analyticsDTO.registration());
        }

        Analytics data = new Analytics();

        data.setEmail(analyticsDTO.email());
        data.setRegistration(analyticsDTO.registration());
        data.setName(analyticsDTO.name());
        data.setBirthDate(analyticsDTO.birthDate());
        data.setMajor(analyticsDTO.major());
        data.setGender(analyticsDTO.gender());
        data.setDepartament(analyticsDTO.departament());
        data.setSemester(analyticsDTO.semester());
        analyticsRepository.save(data);
        return new AnalyticsResponseDTO(data);
    }

    public void groupDynamic(UUID id, GroupDynamicDTO data) {
        Analytics analytics = analyticsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Analytics not found with id: " + id));

        analytics.setTeamwork(data.teamwork().getValue());
        analytics.setProblemSolving(data.problemSolving().getValue());
        analytics.setGroupDynamicObservations(data.groupDynamicObservations());
        analyticsRepository.save(analytics);
    }

    public void individualInterview(UUID id, IndividualInterviewDTO data) {
        Analytics analytics = analyticsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Analytics not found with id: " + id));

        analytics.setCulturalFit(data.culturalFit().getValue());
        analytics.setCommunicationSkills(data.communicationSkills().getValue());
        analytics.setHistoryMotivation(data.historyMotivation());
        analyticsRepository.save(analytics);
    }

    public void traineeFeedback(UUID id, TraineeFeedbackDTO data) {
        Analytics analytics = analyticsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Analytics not found with id: " + id));

        analytics.setSatisfactionLevel(data.satisfactionLevel().getValue());
        analytics.setPerceivedDifficulty(data.perceivedDifficulty().getValue());
        analytics.setCandidateComments(data.candidateComments());
        analyticsRepository.save(analytics);
    }

    public void finalClassification(UUID id, FinalClassificationDTO data) {
        Analytics analytics = analyticsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Analytics not found with id: " + id));

        analytics.setStatus(data.status());
        analytics.setFinalObservations(data.finalObservations());
        analyticsRepository.save(analytics);
    }

    public AnalyticsResponseDTO findById(UUID id) {
        Analytics analytics = analyticsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Analytics not found with id: " + id));

        return new AnalyticsResponseDTO(analytics);
    }

    public Page<AnalyticsResponseDTO> findAll(Pageable pageable) {
        Page<Analytics> analyticsPage = analyticsRepository.findAll(pageable);
        return analyticsPage.map(AnalyticsResponseDTO::new);
    }

}
