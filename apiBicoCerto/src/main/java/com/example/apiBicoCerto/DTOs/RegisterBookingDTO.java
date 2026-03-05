package com.example.apiBicoCerto.DTOs;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record RegisterBookingDTO(
        Integer idService,
        LocalDateTime bookingDate,
        LocalTime startTime,
        LocalTime endTime,
        String notes
) {
}
