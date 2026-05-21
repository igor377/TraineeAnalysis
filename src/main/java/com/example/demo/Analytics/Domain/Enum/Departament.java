package com.example.demo.Analytics.Domain.Enum;

public enum Departament {
    PRESIDENCIA("PRESIDENCIA"),
    VICE_PRESIDENCIA("VICE_PRESIDENCIA"),
    ADMINISTRATIVO_INACEIRO("ADMINISTRATIVO_FINACEIRO"),
    GESTAO_DE_PESSOAS("GESTAO_DE_PESSOAS"),
    COMERCIAL("COMERCIAL"),
    MARKETING("MARKETING"),
    PROJETOS("PROJETOS"),
    TECNOLOGIA("TECNOLOGIA");
    private String departament;
    private Departament(String departament) {
        this.departament = departament;
    }
    public String getDepartament() {
        return this.departament;
    }
}
