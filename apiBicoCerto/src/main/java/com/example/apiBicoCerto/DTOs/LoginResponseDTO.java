package com.example.apiBicoCerto.DTOs;

public record LoginResponseDTO(
        String token,
        String userType
) {
}
