package com.example.apiBicoCerto.controllers;

import com.example.apiBicoCerto.DTOs.InformalWorkerDTO;
import com.example.apiBicoCerto.DTOs.UpResponseIformalWorkerDTO;
import com.example.apiBicoCerto.DTOs.UpdateAddressResponseDTO;
import com.example.apiBicoCerto.DTOs.UpdateInformalWorkerDTO;
import com.example.apiBicoCerto.services.informalWorkerServices.RegisterInformalWorkerService;
import com.example.apiBicoCerto.services.informalWorkerServices.UpdateInformalWorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/informalWorker")
@Tag(name = "InformalWorker", description = "Endpoints responsáveis pelo gerenciamento de informalWorker")
public class InformalWorkerController {

    @Autowired
    private RegisterInformalWorkerService registerInformalWorkerService;

    @Autowired
    private UpdateInformalWorkerService informalWorkerService;

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

    @Operation(
            summary = "Atualizar dados do prestador de serviços",
            description = "Realiza o update parcial ou total de dados do prestador no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida com retorno de dados"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
            @ApiResponse(responseCode = "403", description = "Autenticado, mas sem permissão"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })

    @PatchMapping("/updateProfile/{idInformalWorker}")
    public ResponseEntity<?> updateInformalWorkerProfile(@PathVariable("idInformalWorker") Integer informalWorkerId,@RequestBody UpdateInformalWorkerDTO update){

        try {

            UpResponseIformalWorkerDTO updateProfile = informalWorkerService.updateProfile(informalWorkerId, update);
            return ResponseEntity.ok(updateProfile);

        } catch (IllegalArgumentException e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro de validação"+e.getMessage());

        } catch (SecurityException e){

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acesso negado"+e.getMessage());

        } catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidro ao tentar atualizar dados do prestador de serviços"+e.getMessage());

        }

    }

}