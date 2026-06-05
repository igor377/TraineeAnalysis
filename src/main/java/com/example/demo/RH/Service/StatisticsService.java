package com.example.demo.RH.Service;

import com.example.demo.Analytics.Domain.Enum.Departament;
import com.example.demo.Analytics.Domain.Enum.StatusEnum;
import com.example.demo.Analytics.Repository.AnalyticsRepository;
import com.example.demo.RH.Domain.DTO.GeneralStatiscsDTO;
import com.example.demo.RH.Domain.DTO.GroupDynamicMediaDTO;
import com.example.demo.RH.Domain.DTO.IndividualInterviewMediaDTO;
import com.example.demo.RH.Domain.DTO.TraineeFeedbackMediaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    public List<GeneralStatiscsDTO> generalStatiscs(
            List<StatusEnum> status,
            String semestre,
            Optional<Departament> departament) {

        Departament dep = departament.orElse(null);

        return analyticsRepository.findGeneralStatisticsIn(
                status,
                semestre,
                dep
        );
    }

    public List<GroupDynamicMediaDTO> groupDynamicMedia(
            List<StatusEnum> status,
            String semestre,
            Optional<Departament> departament) {

        Departament dep = departament.orElse(null);

        return analyticsRepository.findGroupDynamicMediaIn(
                status,
                semestre,
                dep
        );
    }

    public List<IndividualInterviewMediaDTO> individualInterview(
            List<StatusEnum> status,
            String semestre,
            Optional<Departament> departament) {

        Departament dep = departament.orElse(null);

        return analyticsRepository.findIndividualInterviewIn(status, semestre, dep);
    }

    public List<TraineeFeedbackMediaDTO> traineeFeedback(
            List<StatusEnum> status,
            String semestre,
            Optional<Departament> departament) {

        Departament dep = departament.orElse(null);

        return analyticsRepository.findFeedbackTraineeIn(status, semestre, dep);
    }
}