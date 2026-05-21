package com.example.demo.User.Domain.DTO;

import com.example.demo.User.Domain.Entity.User;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String email,
        String name
) {

    public UserResponseDTO(User user) {
        this(user.getId(), user.getEmail(), user.getName());
    }
}
