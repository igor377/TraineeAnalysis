package com.example.demo.Analytics.Domain.Enum;

public enum StatusEnum {
    APROVADO("APROVADO"),
    REPROVADO("REPROVADO");

    private String status;
    private StatusEnum(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
}
