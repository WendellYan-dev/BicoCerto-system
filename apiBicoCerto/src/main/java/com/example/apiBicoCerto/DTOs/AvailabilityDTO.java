package com.example.apiBicoCerto.DTOs;

import com.example.apiBicoCerto.enums.DayOfWeekAvailability;
import java.time.LocalTime;

public record AvailabilityDTO(

        DayOfWeekAvailability dayOfWeek,
        LocalTime startTime,
        LocalTime endTime

) {}