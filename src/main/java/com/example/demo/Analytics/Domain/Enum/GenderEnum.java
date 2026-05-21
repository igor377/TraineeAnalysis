package com.example.demo.Analytics.Domain.Enum;

public enum GenderEnum {
    MALE("MALE"),
    FEMALE("FEMALE");
    private String gender;
    private GenderEnum(String gender){
        this.gender = gender;
    }
    public String getGender() {
        return this.gender;
    }
}
