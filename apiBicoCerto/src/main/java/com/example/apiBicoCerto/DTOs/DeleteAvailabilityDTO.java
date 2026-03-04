package com.example.apiBicoCerto.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "DeleteAvailabilityRequest",
        description = "DTO responsável por transportar o ID da disponibilidade a ser removida"
)
public record DeleteAvailabilityDTO(

        @Schema(
                description = "ID da disponibilidade",
                example = "10"
        )
        Integer id
) {
}