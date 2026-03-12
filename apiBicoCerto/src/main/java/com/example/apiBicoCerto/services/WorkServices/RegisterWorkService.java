package com.example.apiBicoCerto.services.WorkServices;


import com.example.apiBicoCerto.DTOs.RegisterWorkDTO;
import com.example.apiBicoCerto.entities.InformalWorker;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.entities.Work;
import com.example.apiBicoCerto.enums.UserStatus;
import com.example.apiBicoCerto.repositories.InformalWorkerRepository;
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
public class RegisterWorkService {

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private GenerateLinkService generateLinkService;

    @Autowired
    private InformalWorkerRepository informalWorkerRepository;



    public void registerService(RegisterWorkDTO registerWorkDTO) throws IOException {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || Objects.requireNonNull(authentication.getPrincipal()).equals("anonymousUser")) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado. Faça login para continuar."
            );
        }

        User loggedUser = (User) authentication.getPrincipal();

        assert loggedUser != null;

        if (loggedUser.getStatus().equals(UserStatus.INATIVO)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Usuário inativado.");
        }

        InformalWorker informalWorker = informalWorkerRepository
                .findByUserId(loggedUser.getId());

        if (informalWorker == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Usuário não é um prestador de serviços");
        }


        if (registerWorkDTO.title() == null || registerWorkDTO.title().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Título do Serviço é obrigatório");
        }

        if (registerWorkDTO.description() == null || registerWorkDTO.description().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Descrição do Serviço é obrigatória");
        }

        if (registerWorkDTO.price() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O preço do serviço é obrigatório");
        } else if (registerWorkDTO.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O preço deve ser maior que zero.");
        }else if (registerWorkDTO.price().scale() > 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O preço não pode ter mais de duas casas decimais");
        }

        Work work = new Work();

        if (registerWorkDTO.image() != null && !registerWorkDTO.image().isEmpty()) {
            String linkUrl = generateLinkService.uploadImage(registerWorkDTO.image());
            work.setUrlPhoto(linkUrl);
        } else {
            work.setUrlPhoto(null);
        }

        work.setTitle(registerWorkDTO.title());
        work.setDescription(registerWorkDTO.description());
        work.setPrice(registerWorkDTO.price());

        work.setInformalWorker(informalWorker);

        workRepository.save(work);

    }
}
