package com.example.apiBicoCerto.controllers;

import com.example.apiBicoCerto.DTOs.UpdateAddressDTO;
import com.example.apiBicoCerto.DTOs.UpdateAddressResponseDTO;
import com.example.apiBicoCerto.DTOs.UpdateUserDTO;
import com.example.apiBicoCerto.DTOs.UserDTO;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.services.addressServices.UpdateAddressService;
import com.example.apiBicoCerto.services.userServices.RegisterUserService;

import com.example.apiBicoCerto.services.userServices.UpdateUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.exc.UnrecognizedPropertyException;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuários", description = "Endpoints responsáveis pelo gerenciamento de usuários"
)
public class UserController {
    @Autowired
    private RegisterUserService registerUserService;

    @Autowired
    private UpdateUserService updateUserService;

    @Autowired
    private UpdateAddressService updateAddressService;

    @Operation(
            summary = "Cadastrar novo usuário",
            description = "Realiza o cadastro de um novo usuário no sistema, incluindo seus endereços vinculados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado (email ou CPF/CNPJ duplicado)"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping(value = "/register",consumes = "multipart/form-data")
    public ResponseEntity<?> registerUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto contendo os dados do usuário", required = true)
            @RequestPart("User") UserDTO userDTO,
            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto) {

        try {
            User user = registerUserService.registerUser(userDTO,profilePhoto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(user);

        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(e.getReason());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao cadastrar usuário.");
        }
    }

    @Operation(
            summary = "Atualizar usuário",
            description = "Realiza o update parcial de dados de um usuário no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida com retorno de dados"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
            @ApiResponse(responseCode = "403", description = "Autenticado, mas sem permissão"),
            @ApiResponse(responseCode = "409", description = "Regra de negócio violada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PatchMapping(value = "/updateProfile", consumes = "multipart/form-data")
    public ResponseEntity<String> updateUser(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto contendo os dados de atualização do usuário",
                    required = true
            )
            @RequestPart("userUpdate") UpdateUserDTO update,

            @RequestPart(value = "profilePhoto", required = false)
            MultipartFile profilePhoto) {

        try {

            updateUserService.updateUser(update,profilePhoto);

            return ResponseEntity
                    .ok("Usuário atualizado com sucesso!");

        } catch (ResponseStatusException e){

            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(e.getMessage());

        } catch (SecurityException e) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Acesso negado" + e.getMessage());

        } catch (IllegalStateException e){

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());

        }  catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao atualizar usuário."+ e.getMessage());
        }

    }

    @Operation(
            summary = "Atualizar endereço",
            description = "Realiza o update parcial ou total de dados do endereço no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Requisição bem-sucedida com retorno de dados"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
            @ApiResponse(responseCode = "403", description = "Autenticado, mas sem permissão"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PatchMapping("/updateAddress/{idAddress}")
    public ResponseEntity<?> updateAddress(@PathVariable("idAddress") Integer id,@RequestBody UpdateAddressDTO update){

        try {
            UpdateAddressResponseDTO address = updateAddressService.updateAddress(id,update);

            return ResponseEntity.ok(address);

        } catch (IllegalArgumentException e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        } catch (SecurityException e){

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acesso negado"+e.getMessage());

        } catch (ResponseStatusException e){

            return ResponseEntity.status(e.getStatusCode())
                    .body(e.getReason());

        } catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidro ao tentar atualizar o endereço"+e.getMessage());

        }



    }
}