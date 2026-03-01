package com.example.apiBicoCerto.DTOs;

import com.example.apiBicoCerto.enums.ServiceCategories;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO responsável pela exibição de dados a serem atualizados do prestador e o id e userName relacionados a ele")
public record UpResponseIformalWorkerDTO(

        @Schema(description = "Identificador do prestador", example = "3")
        Integer informalWorkerId,

        @Schema(description = "Nome de usuário (userName)",example = "wendell_yan")
        String userName,

        @Schema(description = "Categoria de serviço", example = "GARCOM")
        ServiceCategories serviceCategory,

        @Schema(description = "Descrição sobre o prestador", example = "Tenho 5 anos de experiência como garçom em eventos.")
        String aboutMe,

        @Schema(description = "Local onde presta serviço", example = "Aracaju - SE")
        String localService

) {
}
