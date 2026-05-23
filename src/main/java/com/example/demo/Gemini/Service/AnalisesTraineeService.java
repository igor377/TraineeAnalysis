package com.example.demo.Gemini.Service;

import com.example.demo.Analytics.Domain.Entity.Analytics;
import com.example.demo.Analytics.Domain.Enum.StatusEnum;
import com.example.demo.Analytics.Repository.AnalyticsRepository;
import com.example.demo.Gemini.Domain.DTO.AnalisesTraineeDTO;
import com.example.demo.Gemini.Domain.DTO.ProcessAnalyticsDTO;
import com.example.demo.Gemini.Domain.Entity.GeminiAnalytics;
import com.example.demo.Gemini.Domain.Entity.ProcessAnalytics;
import com.example.demo.Gemini.Domain.Enum.JobStatusEnum;
import com.example.demo.Gemini.Repository.AnalisesTraineeRepository;
import com.example.demo.Gemini.Repository.ProcessAnalyticsRepository;
import com.example.demo.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class AnalisesTraineeService {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private AnalyticsRepository analyticsRepository;

    @Autowired
    private AnalisesTraineeRepository analisesTraineeRepository;

    @Autowired
    private ProcessAnalyticsRepository processAnalyticsRepository;

    @Async
    public void processPrompts() {
        List<StatusEnum> statusList = List.of(StatusEnum.REPROVADO);

        int paginaAtual = 0;
        Page<Analytics> paginaResults;

        do {
            Pageable pageable = PageRequest.of(paginaAtual, 10);
            paginaResults = analyticsRepository.findByStatusIn(statusList, pageable);
            List<Analytics> peopleAnalyzed = paginaResults.getContent();

            if (peopleAnalyzed.isEmpty()) {
                break;
            }

            List<GeminiAnalytics> SavedAnalyses = new ArrayList<>();

            for (Analytics data : peopleAnalyzed) {
                StringBuilder promptBuilder = new StringBuilder();
                promptBuilder.append("Instrução OBRIGATÓRIA de formato: Gere a resposta em formato de TEXTO PURO e contínuo. ")
                        .append("NÃO use negritos (**), NÃO use itálicos (*), NÃO use listas ou bullet points (- ou *) e NÃO use aspas duplas no meio do texto. ")
                        .append("Escreva no máximo 3 parágrafos simples e diretos, separados apenas por uma quebra de linha normal.\n\n");
                promptBuilder.append("Analise as caracteristicas do Trainee e forneça um feedback sobre o perfil do trainee, levando em consideração os seguintes dados: ")
                        .append("Perfil do Trainee: ")
                        .append("Nome: ").append(data.getName()).append(", ")
                        .append("Email: ").append(data.getEmail()).append(", ")
                        .append("Registro: ").append(data.getRegistration()).append(", ")
                        .append("Data de Nascimento: ").append(data.getBirthDate()).append(", ")
                        .append("Formação Acadêmica: ").append(data.getMajor()).append(", ")
                        .append("Gênero: ").append(data.getGender()).append(", ")
                        .append("Departamento: ").append(data.getDepartament()).append(", ")
                        .append("Expectativas: ").append(data.getExpectations());

                try {
                    String feedback = geminiService.pedirResposta(promptBuilder.toString());

                    GeminiAnalytics analyzed = new GeminiAnalytics();
                    analyzed.setTraining(data);
                    analyzed.setAvaliacao(feedback);

                    SavedAnalyses.add(analyzed);

                } catch (Exception e) {
                    System.err.println("Erro ao processar trainee ID " + data.getId() + ": " + e.getMessage());
                }
            }

            if (!SavedAnalyses.isEmpty()) {
                analisesTraineeRepository.saveAll(SavedAnalyses);
            }

            paginaAtual++;

        } while (paginaResults.hasNext());
    }

    public List<AnalisesTraineeDTO> getAllAnalises() {
        return analisesTraineeRepository.findAll()
                .stream()
                .map(AnalisesTraineeDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProcessAnalyticsDTO newJob() {
        ProcessAnalytics newJob = new ProcessAnalytics();
        newJob.setJobStatus(JobStatusEnum.IN_PROGRESS);
        newJob.setAnalysis("Analise em processamento");
        ProcessAnalytics saveJob = processAnalyticsRepository.save(newJob);
        executeJob(newJob.getId());
        return new ProcessAnalyticsDTO(newJob);
    }

    @Async
    @Transactional
    public void executeJob(UUID jobId) {
        ProcessAnalytics job = processAnalyticsRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job não encontrado"));

        StringBuilder analyticsBuilder = new StringBuilder();

        try (Stream<GeminiAnalytics> stream = analisesTraineeRepository.findTop100ByTrainingStatusOrderByDataAnalysisDesc(StatusEnum.REPROVADO)) {
            stream.forEach(analysis -> {
                analyticsBuilder.append("Avaliação: ").append(analysis.getAvaliacao());
            });
        } catch (Exception e) {
            job.setJobStatus(JobStatusEnum.FAILED);
            job.setAnalysis("Erro ao ler os dados do banco " + e.getMessage());
            processAnalyticsRepository.save(job);
            return;
        }

        if (analyticsBuilder.isEmpty()){
            job.setJobStatus(JobStatusEnum.COMPLETED);
            job.setAnalysis("O banco de dados esta vazio");
            processAnalyticsRepository.save(job);
            return;
        }

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Instrução OBRIGATÓRIA de formato: Gere a resposta em formato de TEXTO PURO e contínuo. ")
                .append("NÃO use negritos (**), NÃO use itálicos (*), NÃO use listas ou bullet points (- ou *) e NÃO use aspas duplas no meio do texto. ");

        String prompt = promptBuilder.toString() + "\n\n" +
                "Analise as seguintes avaliações de trainees reprovados e forneça insights sobre os principais motivos de reprovação, padrões comuns e sugestões para melhoria. " +
                "Baseie sua análise nas seguintes avaliações:\n\n" + analyticsBuilder.toString();

        try {
            String feedback = geminiService.pedirResposta(prompt);
            job.setJobStatus(JobStatusEnum.COMPLETED);
            job.setAnalysis(feedback);
        } catch (Exception e){
            job.setJobStatus(JobStatusEnum.FAILED);
            job.setAnalysis("Erro na integração com a API do Gemini: " + e.getMessage());
        }

        processAnalyticsRepository.save(job);
    }

    public ProcessAnalyticsDTO findById (UUID jobId) {
        ProcessAnalytics job = processAnalyticsRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job não encontrado"));
        return new ProcessAnalyticsDTO(job);
    }

}
