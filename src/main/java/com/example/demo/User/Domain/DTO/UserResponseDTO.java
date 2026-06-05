package com.example.demo.User.Domain.DTO;

import com.example.demo.User.Domain.Entity.User;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String email,
        String name
) {

    public UserResponseDTO(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public UserResponseDTO(User user) {
        this(user.getId(), user.getEmail(), user.getName());
    }
}
