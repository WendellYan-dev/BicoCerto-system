package com.example.apiBicoCerto.controllers;

import com.example.apiBicoCerto.DTOs.InformalWorkerDTO;
import com.example.apiBicoCerto.services.informalWorkerServices.RegisterInformalWorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/informalWorker")
@Tag(name = "InformalWorker", description = "Endpoints responsáveis pelo gerenciamento de informalWorker")
public class InformalWorkerController {

    @Autowired
    private RegisterInformalWorkerService registerInformalWorkerService;

    @Operation(
            summary = "Cadastrar novo informal worker",
            description = "Realiza o cadastro de um novo informal worker no sistema, incluindo seus endereços vinculados."
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody InformalWorkerDTO dto) {

        try {

            registerInformalWorkerService.registerInformalWorker(dto);

            return ResponseEntity.ok("Prestador cadastrado com sucesso");

        } catch (ResponseStatusException ex) {

            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(ex.getReason());

        } catch (Exception ex) {

            return ResponseEntity
                    .internalServerError()
                    .body("Erro interno no servidor");
        }
    }
}