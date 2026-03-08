package com.example.apiBicoCerto.DTOs;

import com.example.apiBicoCerto.enums.ConfirmStatus;

public record ConfirmBookingDTO(
        Integer bookingId,
        ConfirmStatus confirmStatus
) {
}
