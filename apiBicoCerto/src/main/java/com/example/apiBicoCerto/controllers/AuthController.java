package com.example.apiBicoCerto.controllers;

import com.example.apiBicoCerto.DTOs.AuthDTO;
import com.example.apiBicoCerto.DTOs.LoginResponseDTO;
import com.example.apiBicoCerto.configuration.JWT.TokenService;
import com.example.apiBicoCerto.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
        try {

            var userNamePassword =
                    new UsernamePasswordAuthenticationToken(
                            authDTO.login(),
                            authDTO.password());

            var auth = this.authenticationManager.authenticate(userNamePassword);

            var user = (User) auth.getPrincipal();

            assert user != null;
            var token = tokenService.generateToken(user);

            String userType = user.getUserType().toString();

            return ResponseEntity.ok(new LoginResponseDTO(token, userType));

        } catch (DisabledException e) {
            // Agora o Spring deve cair aqui se o isEnabled() do User retornar false
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário está inativado.");

        } catch (BadCredentialsException e) {
            // Específico para senha incorreta ou usuário não encontrado (por segurança)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login ou senha inválidos");

        } catch (AuthenticationException e) {
            // Se cair aqui, vamos verificar a causa real para não dar 401 genérico sempre
            if (e.getCause() instanceof DisabledException || e.getMessage().contains("inativado")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário está inativado.");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro na autenticação: " + e.getMessage());
        }
    }
}
