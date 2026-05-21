package com.example.demo.User.Domain.DTO;

import jakarta.validation.constraints.Email;

public record UpdateUserDTO(
        @Email
        String email,
        String name) {
}
