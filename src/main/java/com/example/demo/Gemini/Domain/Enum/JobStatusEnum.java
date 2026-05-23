package com.example.demo.Gemini.Domain.Enum;

public enum JobStatusEnum {
    COMPLETED("COMPLETED"),
    IN_PROGRESS("IN_PROGRESS"),
    FAILED("FAILED");

    private String status;

    private JobStatusEnum(String status) {
        this.status = status;
    }
}
