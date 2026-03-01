package com.example.apiBicoCerto.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Schema(description = "DTO responsável pelos dados de um trabalho ofertado pelo prestador de serviço")
public record RegisterWorkDTO(

        @Schema(description = "Título do trabalho", example = "Garçom para evento")
        String title,

        @Schema(description = "Descrição detalhada do trabalho",
                example = "Atuação como garçom em eventos corporativos e festas particulares.")
        String description,

        @Schema(description = "Preço do serviço", example = "150.00")
        BigDecimal price,

        @Schema(description = "Imagem ilustrativa do trabalho (upload de arquivo)")
        MultipartFile image

) {}