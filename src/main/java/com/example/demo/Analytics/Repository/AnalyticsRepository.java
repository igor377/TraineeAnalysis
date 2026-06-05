package com.example.demo.Analytics.Repository;

import com.example.demo.Analytics.Domain.Entity.Analytics;
import com.example.demo.Analytics.Domain.Enum.Departament;
import com.example.demo.Analytics.Domain.Enum.StatusEnum;
import com.example.demo.RH.Domain.DTO.GroupDynamicMediaDTO;
import com.example.demo.RH.Domain.DTO.IndividualInterviewMediaDTO;
import com.example.demo.RH.Domain.DTO.TraineeFeedbackMediaDTO;
import com.example.demo.RH.Domain.DTO.GeneralStatiscsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnalyticsRepository extends JpaRepository<Analytics, UUID> {
    Optional<Analytics> findByEmail(String email);
    Optional<Analytics> findByRegistration(String registration);
    Page<Analytics> findByStatusIn(List<StatusEnum> statusLista, Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByRegistration(String registration);

    @Query("SELECT new com.example.demo.RH.Domain.DTO.GroupDynamicMediaDTO(" +
            "a.departament, a.status, " +
            "AVG(a.teamwork), AVG(a.problemSolving)) " +
            "FROM Analytics a WHERE a.status IN :statusLista AND a.semester = :semestre " +
            "AND (:departament IS NULL OR a.departament = :departament) " +
            "GROUP BY a.departament, a.status")
    List<GroupDynamicMediaDTO> findGroupDynamicMediaIn(
            @Param("statusLista") List<StatusEnum> statusLista,
            @Param("semestre") String semestre,
            @Param("departament") Departament departament);

    @Query("SELECT new com.example.demo.RH.Domain.DTO.IndividualInterviewMediaDTO(" +
            "a.departament, a.status, " +
            "AVG(a.culturalFit), AVG(a.communicationSkills)) " +
            "FROM Analytics a WHERE a.status IN :statusLista AND a.semester = :semestre " +
            "AND (:departament IS NULL OR a.departament = :departament) " +
            "GROUP BY a.departament, a.status")
    List<IndividualInterviewMediaDTO> findIndividualInterviewIn(
            @Param("statusLista") List<StatusEnum> statusLista,
            @Param("semestre") String semestre,
            @Param("departament") Departament departament);

    @Query("SELECT new com.example.demo.RH.Domain.DTO.TraineeFeedbackMediaDTO(" +
            "a.departament, a.status, " +
            "AVG(a.satisfactionLevel), AVG(a.perceivedDifficulty)) " + // ◄── Corrigido: 'perceivedDifficulty'
            "FROM Analytics a WHERE a.status IN :statusLista AND a.semester = :semestre " +
            "AND (:departament IS NULL OR a.departament = :departament) " +
            "GROUP BY a.departament, a.status")
    List<TraineeFeedbackMediaDTO> findFeedbackTraineeIn(
            @Param("statusLista") List<StatusEnum> statusLista,
            @Param("semestre") String semestre,
            @Param("departament") Departament departament);

    @Query("SELECT new com.example.demo.RH.Domain.DTO.GeneralStatiscsDTO(" +
            "a.departament, a.status, " +
            "AVG(a.culturalFit), AVG(a.communicationSkills), " +
            "AVG(a.teamwork), AVG(a.problemSolving), " +
            "AVG(a.satisfactionLevel), AVG(a.perceivedDifficulty)) " + // ◄── Corrigido: 'perceivedDifficulty'
            "FROM Analytics a WHERE a.status IN :statusLista AND a.semester = :semestre " +
            "AND (:departament IS NULL OR a.departament = :departament) " +
            "GROUP BY a.departament, a.status")
    List<GeneralStatiscsDTO> findGeneralStatisticsIn(
            @Param("statusLista") List<StatusEnum> statusLista,
            @Param("semestre") String semestre,
            @Param("departament") Departament departament);
}