package com.example.demo.exceptions.DTO;

import org.springframework.http.HttpStatus;

public record RestErrorMessage(HttpStatus status, String message) {}
