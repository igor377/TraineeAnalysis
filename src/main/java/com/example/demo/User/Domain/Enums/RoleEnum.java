package com.example.demo.User.Domain.Enums;

public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");
    private String role;
    private RoleEnum(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
