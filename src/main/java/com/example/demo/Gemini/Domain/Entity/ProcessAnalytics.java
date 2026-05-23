package com.example.demo.Gemini.Domain.Entity;

import com.example.demo.Gemini.Domain.Enum.JobStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "traineeanalytics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProcessAnalytics {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "analysis", nullable = false, columnDefinition = "TEXT")
    private String analysis;

    private JobStatusEnum jobStatus;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
