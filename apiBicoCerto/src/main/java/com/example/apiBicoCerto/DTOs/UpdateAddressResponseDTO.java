package com.example.apiBicoCerto.DTOs;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO responsável pela exibição de dados a serem atualizados do endereço e o id e userName relacionados a esse endereço")
public record UpdateAddressResponseDTO(

        @Schema(description = "Identificador único do endereço",example = "10")
        Integer id,

        @Schema(description = "CEP", example = "49000000")
        String postalCode,

        @Schema(description = "Bairro", example = "Centro")
        String neighborhood,

        @Schema(description = "Estado", example = "SE")
        String state,

        @Schema(description = "Rua ou logradouro", example = "Rua A")
        String street,

        @Schema(description = "Complemento", example = "Apto 2")
        String complement,

        @Schema(description = "Número da residência", example = "100")
        String number,

        @Schema(description = "Indica se é o endereço principal", example = "true")
        Boolean isPrimary,

        @Schema(description = "Identificador do usuário proprietário do endereço", example = "3")
        Integer userId,

        @Schema(description = "Nome de usuário (userName) do proprietário do endereço",example = "wendell_yan")
        String userName

) {}
