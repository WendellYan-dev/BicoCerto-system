package com.example.apiBicoCerto.controllers;

import com.example.apiBicoCerto.DTOs.UserDTO;
import com.example.apiBicoCerto.services.userServices.RegisterUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuários", description = "Endpoints responsáveis pelo gerenciamento de usuários"
)
public class UserController {

    @Autowired
    private RegisterUserService registerUserService;

    @Operation(summary = "Cadastrar novo usuário", description = "Realiza o cadastro de um novo usuário no sistema, incluindo seus endereços vinculados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado (email ou CPF/CNPJ duplicado)"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto contendo os dados do usuário", required = true)
            @RequestBody UserDTO userDTO) {

        try {
            registerUserService.registerUser(userDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Usuário cadastrado com sucesso.");

        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(e.getReason());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao cadastrar usuário.");
        }
    }
}