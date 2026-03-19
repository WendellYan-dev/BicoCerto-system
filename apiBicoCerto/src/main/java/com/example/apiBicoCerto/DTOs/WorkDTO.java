package com.example.apiBicoCerto.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Schema(description = "DTO responsável pelo transporte de dados do serviço")
public record WorkDTO(
        @Schema(description = "Título do trabalho", example = "Eletricista residencial")
        String title,

        @Schema(description = "Descrição detalhada do trabalho", example = "Instalação e manutenção elétrica")
        String description,

        @Schema(description = "Preço do serviço", example = "150.00")
        BigDecimal price,

        @Schema(
                description = "Imagem ilustrativa do trabalho"
        )
        String imageUrl
) {


}
