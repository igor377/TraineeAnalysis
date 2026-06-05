package com.example.demo.Analytics.Domain.Enum;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Classification {
    PESSIMO(1, "Péssimo"),
    RUIM(2, "Ruim"),
    REGULAR(3, "Regular"),
    BOM(4, "Bom"),
    OTIMO(5, "Ótimo");

    private final int value;
    private final String description;

    Classification(int value, String description) {
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

    public static Classification fromValue(Integer value) {
        if (value == null) {
            return null; // Se o banco for nulo, o DTO recebe nulo de boa
        }
        for (Classification c : Classification.values()) {
            if (c.getValue() == value) { // Ou o método que retorna o int do seu enum
                return c;
            }
        }
        throw new IllegalArgumentException("Valor inválido para o Enum Classification: " + value);
    }
}
