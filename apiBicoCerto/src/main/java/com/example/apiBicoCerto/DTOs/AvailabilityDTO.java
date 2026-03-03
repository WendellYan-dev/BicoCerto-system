package com.example.apiBicoCerto.DTOs;

import com.example.apiBicoCerto.enums.DayOfWeek;
import java.time.LocalTime;

public record AvailabilityDTO(

        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime

) {}