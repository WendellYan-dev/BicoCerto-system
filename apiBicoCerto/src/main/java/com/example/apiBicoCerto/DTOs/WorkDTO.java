package com.example.apiBicoCerto.DTOs;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record WorkDTO(
    String title,
    String description,
    BigDecimal price,
    MultipartFile image,
    Integer id_informal_worker
    ){}
