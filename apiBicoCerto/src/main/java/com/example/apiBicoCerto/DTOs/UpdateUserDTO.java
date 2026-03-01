package com.example.apiBicoCerto.DTOs;


import com.example.apiBicoCerto.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;


@Schema(description = "DTO responsável pelo transporte de dados a serem atualizados do usuário")
public record UpdateUserDTO(

        @Schema(description = "Email do usuário", example = "igor@email.com")
        String email,

        @Schema(description = "Primeiro nome", example = "Igor")
        String firstName,

        @Schema(description = "Sobrenome", example = "Seara")
        String lastName,

        @Schema(description = "Telefone de contato", example = "79999999999")
        String phoneNumber,

        @Schema(description = "Data de nascimento", example = "2000-05-10")
        LocalDate birthDate,

        @Schema(description = "Senha do usuário", example = "123456")
        String password,

        @Schema(description = "URL da foto de perfil")
        String profilePhoto,

        @Schema(description = "Status atual do usuário", example = "ATIVO")
        UserStatus status

) {}
