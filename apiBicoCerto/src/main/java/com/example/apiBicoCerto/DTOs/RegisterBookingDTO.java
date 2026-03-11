package com.example.apiBicoCerto.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "DTO responsável pelo registro de um novo agendamento")
public record RegisterBookingDTO(

        @Schema(
                description = "ID do serviço que será agendado",
                example = "3",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Integer idService,

        @Schema(
                description = "Data do agendamento",
                example = "2026-03-15",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalDate bookingDate,

        @Schema(
                description = "Horário de início do serviço",
                example = "14:00",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalTime startTime,

        @Schema(
                description = "Horário de término do serviço",
                example = "15:00",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalTime endTime,

        @Schema(
                description = "Observações adicionais sobre o serviço",
                example = "Levar ferramentas necessárias"
        )
        String notes
) {
}