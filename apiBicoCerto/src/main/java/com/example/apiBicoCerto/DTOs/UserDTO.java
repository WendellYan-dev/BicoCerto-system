package com.example.apiBicoCerto.DTOs;

import com.example.apiBicoCerto.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "DTO responsável pelo transporte de dados do usuário")
public record UserDTO(

        @Schema(description = "Nome de usuário único", example = "igor123")
        String userName,

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

        @Schema(description = "CPF do usuário (caso pessoa física)", example = "12345678900")
        String cpf,

        @Schema(description = "CNPJ do usuário (caso pessoa jurídica)", example = "12345678000199")
        String cnpj,

        @Schema(description = "URL da foto de perfil")
        String profilePhoto,

        @Schema(description = "Data de registro no sistema", example = "2026-02-26")
        LocalDate registerDate,

        @Schema(description = "Status atual do usuário", example = "ATIVO")
        UserStatus status,

        @Schema(description = "Lista de endereços associados ao usuário")
        List<AddressDTO> addresses
) {}