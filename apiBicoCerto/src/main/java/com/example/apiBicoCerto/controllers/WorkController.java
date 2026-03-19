package com.example.apiBicoCerto.controllers;

import com.example.apiBicoCerto.DTOs.EditWorkDTO;
import com.example.apiBicoCerto.DTOs.RegisterWorkDTO;
import com.example.apiBicoCerto.DTOs.SearchWorkDTO;
import com.example.apiBicoCerto.entities.Work;
import com.example.apiBicoCerto.services.workServices.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/work")
@Tag(name = "Works", description = "Endpoints responsáveis pelo gerenciamento de serviços ofertados pelos prestadores")
public class WorkController {

    @Autowired
    private RegisterWorkService registerWorkService;

    @Autowired
    private EditWorkService editWorkService;

    @Autowired
    private DeleteWorkService deleteWorkService;

    @Autowired
    private SearchWorkService searchWorkService;

    @Autowired
    private ListWorksService listWorksService;


    // ============================= CREATE =============================

    @PostMapping(path = "/register", consumes = "multipart/form-data")
    @Operation(
            summary = "Cadastrar um novo serviço",
            description = "Endpoint responsável por cadastrar um novo serviço com upload de imagem",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Usuário não é um prestador"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    public ResponseEntity<?> registerWork(@ModelAttribute RegisterWorkDTO workDTO) {

        try {
            Work work = registerWorkService.registerService(workDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(work.getId());

        } catch (ResponseStatusException ex) {
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(ex.getReason());

        } catch (IOException ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar upload da imagem.");

        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao criar o serviço.");
        }
    }


    // ============================= UPDATE =============================

    @PatchMapping(path = "/edit/{id}", consumes = "multipart/form-data")
    @Operation(
            summary = "Editar um serviço",
            description = "Endpoint responsável por editar parcialmente um serviço existente (apenas o dono pode editar)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão para editar"),
                    @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    public ResponseEntity<?> editWork(@PathVariable Integer id,
                                      @ModelAttribute EditWorkDTO editWorkDTO) {

        try {
            editWorkService.editWork(id, editWorkDTO);
            return ResponseEntity.noContent().build();

        } catch (ResponseStatusException ex) {
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(ex.getReason());

        } catch (IOException ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar upload da imagem.");

        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao editar o serviço.");
        }
    }


    // ============================= DELETE =============================

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Excluir um serviço",
            description = "Endpoint responsável por excluir um serviço existente (apenas o dono pode excluir)",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Serviço excluído com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão para excluir"),
                    @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    public ResponseEntity<?> deleteWork(@PathVariable Integer id) {

        try {
            deleteWorkService.deleteWork(id);
            return ResponseEntity.noContent().build();

        } catch (ResponseStatusException ex) {
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(ex.getReason());

        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao excluir o serviço.");
        }
    }


    // ============================= SEARCH =============================

    @GetMapping("/search/{id}")
    @Operation(
            summary = "Buscar um serviço por ID",
            description = "Endpoint responsável por retornar os dados de um serviço específico pelo seu identificador",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Serviço encontrado com sucesso",
                            content = @Content(schema = @Schema(implementation = SearchWorkDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    public ResponseEntity<?> searchWork(@PathVariable Integer id) {

        try {
            SearchWorkDTO searchWorkDTO = searchWorkService.searchWork(id);
            return ResponseEntity.ok(searchWorkDTO);

        } catch (ResponseStatusException ex) {
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(ex.getReason());

        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao buscar o serviço.");
        }
    }

    @GetMapping("/list")
    @Operation(
            summary = "Listar serviços",
            description = "Lista todos os serviços ou filtra por título",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    public ResponseEntity<?> listWorks(@RequestParam(required = false) String title, @RequestParam(required = false)BigDecimal price,  @PageableDefault(size = 10, sort = "title") Pageable pageable) {

        try {
            return ResponseEntity.ok(listWorksService.listWorks(title, price, pageable));

        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar serviços.");
        }
    }
}
