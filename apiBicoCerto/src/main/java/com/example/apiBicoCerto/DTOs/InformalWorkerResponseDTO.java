package com.example.apiBicoCerto.DTOs;

import com.example.apiBicoCerto.enums.ServiceCategories;
import io.swagger.v3.oas.annotations.media.Schema;

public record InformalWorkerResponseDTO(

        String userName,

        ServiceCategories serviceCategory,

        String aboutMe,

        String localService
) {
}
