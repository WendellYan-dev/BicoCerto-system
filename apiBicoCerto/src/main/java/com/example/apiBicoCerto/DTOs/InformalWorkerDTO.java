package com.example.apiBicoCerto.DTOs;


import com.example.apiBicoCerto.enums.ServiceCategories;
import com.example.apiBicoCerto.enums.UserStatus;
import com.example.apiBicoCerto.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;


@Schema(description = "DTO responsável pelos dados do prestador de serviço informal")
public record InformalWorkerDTO(

        @Schema(description = "Nome de usuário", example = "joaosilva")
        String userName,

        @Schema(description = "Email", example = "joao.silva@email.com")
        String email,

        @Schema(description = "Primeiro nome", example = "João")
        String firstName,

        @Schema(description = "Sobrenome", example = "Silva")
        String lastName,

        @Schema(description = "Telefone", example = "79999999999")
        String phoneNumber,

        @Schema(description = "Data de nascimento", example = "1995-05-10")
        LocalDate birthDate,

        @Schema(description = "Senha", example = "Senha@123")
        String password,

        @Schema(description = "CPF", example = "52998224725")
        String cpf,

        @Schema(description = "CNPJ", example = "null")
        String cnpj,

        @Schema(description = "Foto de perfil", example = "https://meusite.com/foto.jpg")
        String profilePhoto,

        @Schema(description = "Data de cadastro", example = "2026-02-28")
        LocalDate registerDate,

        @Schema(description = "Status do usuário", example = "ATIVO")
        UserStatus status,

        @Schema(
                description = "Lista de endereços",
                example = """
            [
              {
                "postalCode": "49000000",
                "street": "Rua A",
                "neighborhood": "Centro",
                "state": "SE",
                "number": "100",
                "complement": "Apto 2",
                "isPrimary": true
              }
            ]
            """
        )
        List<AddressDTO> addresses,

        @Schema(description = "Tipo do usuário", example = "PRESTADOR")
        UserType userType,

        // ---- Campos específicos do InformalWorker ----

        @Schema(description = "Categoria de serviço", example = "GARCOM")
        ServiceCategories serviceCategory,

        @Schema(description = "Descrição sobre o prestador", example = "Tenho 5 anos de experiência como garçom em eventos.")
        String aboutMe,

        @Schema(description = "Local onde presta serviço", example = "Aracaju - SE")
        String localService

) {}