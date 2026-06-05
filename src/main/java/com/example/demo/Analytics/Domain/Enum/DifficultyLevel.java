package com.example.demo.Analytics.Domain.Enum;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DifficultyLevel {
    VERY_EASY(1, "Muito Fácil"),
    EASY(2, "Fácil"),
    MEDIUM(3, "Médio"),
    HARD(4, "Difícil"),
    VERY_HARD(5, "Muito Difícil");

    private final int value;
    private final String description;

    DifficultyLevel(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static DifficultyLevel fromValue(Integer value) {
        if (value == null) {
            return null; // Se o banco for nulo, o DTO recebe nulo de boa
        }
        for (DifficultyLevel c : DifficultyLevel.values()) {
            if (c.getValue() == value) { // Ou o método que retorna o int do seu enum
                return c;
            }
        }
        throw new IllegalArgumentException("Valor inválido para o Enum Classification: " + value);
    }
}
