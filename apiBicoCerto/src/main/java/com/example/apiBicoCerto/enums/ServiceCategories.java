package com.example.apiBicoCerto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonCreator
    public static ServiceCategories fromValue(String value) {

        for (ServiceCategories category : ServiceCategories.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }

        return null; // 👈 impede o Jackson de quebrar
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}