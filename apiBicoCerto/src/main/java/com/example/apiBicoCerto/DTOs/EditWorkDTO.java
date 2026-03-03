package com.example.apiBicoCerto.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Schema(description = "DTO responsável pela edição de um trabalho ofertado pelo prestador de serviço. Todos os campos são opcionais.")
public record EditWorkDTO(

        @Schema(description = "Novo título do trabalho",
                example = "Garçom profissional para eventos")
        String title,

        @Schema(description = "Nova descrição detalhada do trabalho",
                example = "Experiência em eventos corporativos, casamentos e festas privadas.")
        String description,

        @Schema(description = "Novo preço do serviço",
                example = "200.00")
        BigDecimal price,

        @Schema(description = "Nova imagem ilustrativa do trabalho (upload de arquivo)")
        MultipartFile image

) {}