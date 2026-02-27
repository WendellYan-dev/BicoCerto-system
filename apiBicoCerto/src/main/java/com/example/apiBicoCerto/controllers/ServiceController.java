package com.example.apiBicoCerto.controllers;

import com.example.apiBicoCerto.services.workerserviceServices.RegisterWorkerserviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/service")
@Tag(name = "Serviços", description = "Endpoints responsáveis pelo gerenciamento de Serviços")
public class ServiceController {

    @Autowired
    private RegisterWorkerserviceService registerWorkerserviceService;

    @PostMapping(
            value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
            summary = "Cadastrar novo serviço",
            description = "Realiza o cadastro de um serviço com upload de imagem."
    )
    public ResponseEntity<?> registerService(
            @RequestPart("image") MultipartFile image) {

        try {
            registerWorkerserviceService.registerService(image);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
