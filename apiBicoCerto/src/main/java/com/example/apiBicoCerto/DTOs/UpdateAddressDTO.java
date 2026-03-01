package com.example.apiBicoCerto.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO responsável pelo transporte de dados a serem atualizados do endereço")
public record UpdateAddressDTO(

        @Schema(description = "CEP", example = "49000000")
        String postalCode,

        @Schema(description = "Rua ou logradouro", example = "Rua A")
        String street,

        @Schema(description = "Bairro", example = "Centro")
        String neighborhood,

        @Schema(description = "Estado", example = "SE")
        String state,

        @Schema(description = "Número da residência", example = "100")
        String number,

        @Schema(description = "Complemento", example = "Apto 2")
        String complement,

        @Schema(description = "Indica se é o endereço principal", example = "true")
        Boolean isPrimary

) {}
