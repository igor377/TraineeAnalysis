package com.example.demo.RH.Service;

import com.example.demo.RH.Domain.DTO.RHConfigDTO;
import com.example.demo.RH.Domain.DTO.RHConfigResponseDTO;
import com.example.demo.RH.Domain.Entity.RH;
import com.example.demo.RH.Repository.RHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RHService {

    @Autowired
    private RHRepository RHRepository;

    public RHConfigResponseDTO create (RHConfigDTO data) {
        if (RHRepository.existsByDate(data.date())) {
            throw new RuntimeException("Já existe um registro para essa data.");
        }

        RH newRH = new RH();
        newRH.setDate(data.date());
        newRH.setType(data.type());

        RH savedRH = RHRepository.save(newRH);

        return new RHConfigResponseDTO(savedRH);
    }

    public RHConfigResponseDTO getByDate (String date) {
        RH rh = RHRepository.findByDate(date)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado para a data: " + date));
        return new RHConfigResponseDTO(rh);
    }
}
