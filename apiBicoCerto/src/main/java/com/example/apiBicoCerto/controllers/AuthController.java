package com.example.apiBicoCerto.controllers;

import com.example.apiBicoCerto.DTOs.AuthDTO;
import com.example.apiBicoCerto.DTOs.LoginResponseDTO;
import com.example.apiBicoCerto.configuration.JWT.TokenService;
import com.example.apiBicoCerto.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("auth")
@Tag(name = "Login", description = "Endpoint responsável pelo Login")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Operation(
            summary = "Logar",
            description = "Executa o login."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthDTO authDTO){
        var userNamePassword = new UsernamePasswordAuthenticationToken(authDTO.login(),authDTO.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((User) Objects.requireNonNull(auth.getPrincipal()));

        String userType = ((User) auth.getPrincipal()).getUserType().toString();

        return ResponseEntity.ok(new LoginResponseDTO(token,userType));
    }
}
