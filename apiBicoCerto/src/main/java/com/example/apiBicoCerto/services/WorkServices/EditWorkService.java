package com.example.apiBicoCerto.services.WorkServices;

import com.example.apiBicoCerto.DTOs.EditWorkDTO;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.entities.Work;
import com.example.apiBicoCerto.enums.UserStatus;
import com.example.apiBicoCerto.repositories.WorkRepository;
import com.example.apiBicoCerto.utils.GenerateLinkService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

@Service
@Transactional
public class EditWorkService {

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private GenerateLinkService generateLinkService;

    public void editWork(Integer workId, EditWorkDTO editWorkDTO) throws IOException {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || Objects.equals(authentication.getPrincipal(), "anonymousUser")) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado. Faça login para continuar."
            );
        }

        User loggedUser = (User) authentication.getPrincipal();

        // Buscar o work
        Work work = workRepository.findById(workId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Serviço não encontrado"
                        )
                );

        // Verificar se o work pertence ao usuário logado
        assert loggedUser != null;

        if (loggedUser.getStatus().equals(UserStatus.INATIVO)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Usuário inativado.");
        }

        if (!work.getInformalWorker()
                .getUser()
                .getId()
                .equals(loggedUser.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não tem permissão para alterar este serviço"
            );
        }

        if (editWorkDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Corpo da requisição não enviado."
            );
        }

        if (editWorkDTO.title() == null &&
                editWorkDTO.description() == null &&
                editWorkDTO.price() == null &&
                editWorkDTO.image() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nenhum campo foi enviado para atualização."
            );
        }

        if (editWorkDTO.description() != null) {
            if (editWorkDTO.description().trim().isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "A descrição não pode estar vazia."
                );
            }
            work.setDescription(editWorkDTO.description());
        }

        if (editWorkDTO.price() != null) {
            if (editWorkDTO.price().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "O preço deve ser maior que zero.");
            }else if (editWorkDTO.price().scale() > 2) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "O preço não pode ter mais de duas casas decimais");
            }
            work.setPrice(editWorkDTO.price());
        }

        if (editWorkDTO.image() != null && !editWorkDTO.image().isEmpty()) {
            String linkUrl = generateLinkService.uploadImage(editWorkDTO.image());
            work.setUrlPhoto(linkUrl);
        }

        workRepository.save(work);
    }
}
