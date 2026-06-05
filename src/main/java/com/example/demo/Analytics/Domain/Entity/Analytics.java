package com.example.demo.Analytics.Domain.Entity;

import com.example.demo.Analytics.Domain.Enum.Classification;
import com.example.demo.Analytics.Domain.Enum.Departament;
import com.example.demo.Analytics.Domain.Enum.GenderEnum;
import com.example.demo.Analytics.Domain.Enum.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "analytics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Analytics {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String registration;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String major;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderEnum gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Departament departament;

    @Column(nullable = false)
    private String semester;

    // Group Dynamic

    private Integer teamwork;

    private Integer problemSolving;

    private String groupDynamicObservations;

    // Individual Interview

    private Integer culturalFit;

    private Integer communicationSkills;

    private String historyMotivation;

    // Trainee Feedback

    private Integer satisfactionLevel;

    private Integer perceivedDifficulty;

    private String candidateComments;

    // Final Classification

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private String finalObservations;
}
