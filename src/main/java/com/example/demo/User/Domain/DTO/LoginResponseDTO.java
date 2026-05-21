package com.example.demo.User.Domain.DTO;

import jakarta.validation.constraints.NotBlank;

public record LoginResponseDTO(
        @NotBlank String token
) {
}
