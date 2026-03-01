package com.example.apiBicoCerto.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "DTO responsável por representar um trabalho retornado na busca")
public record SearchWorkDTO(

        @Schema(description = "Identificador único do serviço",
                example = "1",
                accessMode = Schema.AccessMode.READ_ONLY)
        Integer id,

        @Schema(description = "Título do trabalho",
                example = "Garçom para evento",
                accessMode = Schema.AccessMode.READ_ONLY)
        String title,

        @Schema(description = "Descrição detalhada do trabalho",
                example = "Atuação como garçom em eventos corporativos e festas particulares.",
                accessMode = Schema.AccessMode.READ_ONLY)
        String description,

        @Schema(description = "Preço do serviço",
                example = "150.00",
                accessMode = Schema.AccessMode.READ_ONLY)
        BigDecimal price,

        @Schema(description = "URL da imagem ilustrativa do trabalho",
                example = "https://res.cloudinary.com/seu-projeto/image/upload/v123456789/work.jpg",
                accessMode = Schema.AccessMode.READ_ONLY)
        String urlPhoto

) {}