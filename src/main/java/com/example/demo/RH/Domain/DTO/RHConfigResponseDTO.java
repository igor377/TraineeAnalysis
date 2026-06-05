package com.example.demo.RH.Domain.DTO;

import com.example.demo.RH.Domain.Entity.RH;

public record RHConfigResponseDTO(
        String date,
        String type
) {

    public RHConfigResponseDTO (RH data) {
        this(data.getDate(), data.getType());
    }
}
