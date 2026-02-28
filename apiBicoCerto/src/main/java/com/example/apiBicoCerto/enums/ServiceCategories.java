package com.example.apiBicoCerto.enums;

public enum ServiceCategories {

    GARCOM("Garçom"),
    DIARISTA("Diarista"),
    PET("Pet"),
    JARDINEIRO("Jardineiro(a)"),
    MAQUIADOR("Maquiador(a)");

    private final String descricao;

    ServiceCategories(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
