package com.example.apiBicoCerto.controllers;

import com.example.apiBicoCerto.DTOs.AvailabilityDTO;
import com.example.apiBicoCerto.DTOs.DeleteAvailabilityDTO;
import com.example.apiBicoCerto.exceptions.NotFoundException;
import com.example.apiBicoCerto.services.availabilityServices.DeleteAvailabilityService;
import com.example.apiBicoCerto.services.availabilityServices.RegisterAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/availability")
@Tag(name = "Availability", description = "Endpoints responsáveis pelo gerenciamento de disponibilidade dos prestadores")
public class AvailabilityController {

    @Autowired
    private RegisterAvailabilityService registerAvailabilityService;

    @Autowired
    private DeleteAvailabilityService deleteAvailabilityService;


    // ============================= CREATE =============================

    @PostMapping("/register")
    @Operation(
            summary = "Cadastrar disponibilidades",
            description = "Endpoint responsável por cadastrar uma lista de horários de disponibilidade para o prestador logado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Disponibilidades cadastradas com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Conflito de horários ou dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "404", description = "Prestador não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    public ResponseEntity<?> registerAvailability(@RequestBody @Valid List<AvailabilityDTO> availabilityDTOList) {

        try {
            registerAvailabilityService.registerAvailability(availabilityDTOList);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (ResponseStatusException ex) {
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(ex.getReason());

        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao cadastrar disponibilidade.");
        }
    }


    @DeleteMapping("/delete")
    @Operation(
            summary = "Deletar disponibilidades",
            description = "Endpoint responsável por deletar uma lista de horários de disponibilidade para o prestador logado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Disponibilidades deletadas com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Conflito de horários ou dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "404", description = "Prestador não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    public ResponseEntity<String> delAvailability(@RequestBody List<DeleteAvailabilityDTO> idAvailability){

        try {

            deleteAvailabilityService.deleteAvailability(idAvailability);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (ResponseStatusException e) {

            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(e.getReason());

        } catch (NotFoundException e){

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (Exception ex) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao deletar disponibilidade.");

        }

    }

}