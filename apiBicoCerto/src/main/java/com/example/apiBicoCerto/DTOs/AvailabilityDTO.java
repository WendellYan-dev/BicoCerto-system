package com.example.apiBicoCerto.DTOs;

import com.example.apiBicoCerto.enums.DayOfWeekAvailability;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record AvailabilityDTO(

        @NotNull(message = "Dia da semana é obrigatório")
        DayOfWeekAvailability dayOfWeek,

        @NotNull(message = "Horário Inicial é obrigatório")
        LocalTime startTime,

        @NotNull(message = "Horário final é Obrigatório")
        LocalTime endTime

) {}