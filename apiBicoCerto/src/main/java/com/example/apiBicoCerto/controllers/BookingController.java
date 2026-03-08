package com.example.apiBicoCerto.controllers;

import com.example.apiBicoCerto.DTOs.ConfirmBookingDTO;
import com.example.apiBicoCerto.DTOs.RegisterBookingDTO;
import com.example.apiBicoCerto.services.bookingServices.ConfirmBookingService;
import com.example.apiBicoCerto.services.bookingServices.RegisterBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/booking")
@Tag(name = "Booking", description = "Endpoints responsáveis pelo gerenciamento de agendamentos")
public class BookingController {

    @Autowired
    private RegisterBookingService registerBookingService;

    @Autowired
    private ConfirmBookingService confirmBookingService;


    // ============================= CREATE =============================

    @PostMapping("/register")
    @Operation(
            summary = "Criar agendamento",
            description = "Endpoint responsável por criar um novo agendamento para um serviço",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos ou conflito de horário"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    public ResponseEntity<?> registerBooking(@RequestBody RegisterBookingDTO registerBookingDTO) {

        try {

            registerBookingService.registerBooking(registerBookingDTO);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build();

        } catch (ResponseStatusException e) {

            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(e.getReason());

        } catch (EntityNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao realizar agendamento.");

        }
    }
    @PatchMapping("/confirm")
    @Operation(
            summary = "Confirmar ou recusar agendamento",
            description = "Endpoint para o prestador confirmar ou recusar uma solicitação de agendamento",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Agendamento processado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Agendamento já processado ou dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Usuário não tem permissão"),
                    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )

    public ResponseEntity<?> confirmBooking(@RequestBody ConfirmBookingDTO confirmBookingDTO) {

        try {

            confirmBookingService.confirmBooking(confirmBookingDTO);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();

        } catch (ResponseStatusException e) {

            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(e.getReason());

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao processar agendamento.");
        }
    }
}