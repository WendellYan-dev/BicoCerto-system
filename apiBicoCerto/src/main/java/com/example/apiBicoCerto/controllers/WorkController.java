package com.example.apiBicoCerto.controllers;

import com.example.apiBicoCerto.DTOs.WorkDTO;
import com.example.apiBicoCerto.services.WorkServices.RegisterWorkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/work")
@Tag(name = "Works", description = "Endpoints responsáveis pelo gerenciamento de Serviços")
public class WorkController {

    @Autowired
    private RegisterWorkService registerWorkService;


    @PostMapping(consumes = "multipart/form-data")
    @Operation(
            summary = "Criar um novo trabalho",
            description = "Endpoint responsável por cadastrar um novo trabalho com imagem",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trabalho criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição",
                            content = @Content(schema = @Schema(implementation = String.class)))
            }
        )
    public ResponseEntity<?> createWork(@ModelAttribute WorkDTO workDTO) throws IOException {
            registerWorkService.registerService(workDTO);
            return ResponseEntity.ok().build();
        }
    }

