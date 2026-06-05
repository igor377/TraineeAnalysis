package com.example.demo.RH.Controller;

import com.example.demo.Analytics.Domain.Enum.Departament;
import com.example.demo.Analytics.Domain.Enum.StatusEnum;
import com.example.demo.RH.Domain.DTO.GeneralStatiscsDTO;
import com.example.demo.RH.Domain.DTO.GroupDynamicMediaDTO;
import com.example.demo.RH.Domain.DTO.IndividualInterviewMediaDTO;
import com.example.demo.RH.Domain.DTO.TraineeFeedbackMediaDTO;
import com.example.demo.RH.Service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/general")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<GeneralStatiscsDTO>> generalStatistics(
            @RequestParam List<StatusEnum> status,
            @RequestParam String semestre,
            @RequestParam(required = false) Departament departament
            ) {
        Optional<Departament> deptOpt = Optional.ofNullable(departament);

        List<GeneralStatiscsDTO> stats = statisticsService.generalStatiscs(status, semestre, deptOpt);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/group-dynamic")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<GroupDynamicMediaDTO>> groupDynamicMedia(
            @RequestParam List<StatusEnum> status,
            @RequestParam String semestre,
            @RequestParam(required = false) Departament departament
    ) {
        Optional<Departament> deptOpt = Optional.ofNullable(departament);

        List<GroupDynamicMediaDTO> stats = statisticsService.groupDynamicMedia(status, semestre, deptOpt);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/individual-interview")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<IndividualInterviewMediaDTO>> individualInterview(
            @RequestParam List<StatusEnum> status,
            @RequestParam String semestre,
            @RequestParam(required = false) Departament departament
    ) {
        Optional<Departament> deptOpt = Optional.ofNullable(departament);

        List<IndividualInterviewMediaDTO> stats = statisticsService.individualInterview(status, semestre, deptOpt);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/trainee-feedback")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<TraineeFeedbackMediaDTO>> traineeFeedback(
            @RequestParam List<StatusEnum> status,
            @RequestParam String semestre,
            @RequestParam(required = false) Departament departament
    ) {
        Optional<Departament> deptOpt = Optional.ofNullable(departament);

        List<TraineeFeedbackMediaDTO> stats = statisticsService.traineeFeedback(status, semestre, deptOpt);
        return ResponseEntity.ok(stats);
    }
}