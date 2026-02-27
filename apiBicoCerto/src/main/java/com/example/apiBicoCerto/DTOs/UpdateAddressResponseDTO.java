package com.example.apiBicoCerto.DTOs;

public record UpdateAddressResponseDTO(
        Integer id,
        String postalCode,
        String neighborhood,
        String state,
        String street,
        String complement,
        String number,
        Boolean isPrimary,
        Integer userId,
        String userName

) {}
