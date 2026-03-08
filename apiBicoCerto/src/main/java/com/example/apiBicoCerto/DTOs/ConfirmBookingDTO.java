package com.example.apiBicoCerto.DTOs;

import com.example.apiBicoCerto.enums.ConfirmStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO responsável pela confirmação ou recusa de um agendamento")
public record ConfirmBookingDTO(

        @Schema(
                description = "ID do agendamento",
                example = "10",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Integer bookingId,

        @Schema(
                description = "Ação a ser tomada sobre o agendamento",
                example = "CONFIRMAR",
                allowableValues = {"CONFIRMAR", "RECUSAR"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        ConfirmStatus confirmStatus
) {
}