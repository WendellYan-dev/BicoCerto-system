package com.example.apiBicoCerto.DTOs;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO responsável pelo transporte de dados a serem atualizados do prestador de serviço")
public record UpdateInformalWorkerDTO(

        @Schema(description = "Categoria de serviço", example = "GARCOM")
        String serviceCategory,

        @Schema(description = "Descrição sobre o prestador", example = "Tenho 5 anos de experiência como garçom em eventos.")
        String aboutMe,

        @Schema(description = "Local onde presta serviço", example = "Aracaju - SE")
        String localService

) {}
