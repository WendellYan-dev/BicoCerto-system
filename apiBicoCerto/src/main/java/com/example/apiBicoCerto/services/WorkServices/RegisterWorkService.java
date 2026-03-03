package com.example.apiBicoCerto.services.WorkServices;


import com.example.apiBicoCerto.DTOs.RegisterWorkDTO;
import com.example.apiBicoCerto.entities.InformalWorker;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.entities.Work;
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

        InformalWorker informalWorker = informalWorkerRepository
                .findByUserId(loggedUser.getId());

        if (informalWorker == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Usuário não é um prestador de serviços");
        }

        String linkUrl = generateLinkService.uploadImage(registerWorkDTO.image());
        Work work = new Work();
        work.setDescription(registerWorkDTO.title());
        work.setTitle(registerWorkDTO.description());
        work.setPrice(registerWorkDTO.price());
        work.setUrlPhoto(linkUrl);
        work.setInformalWorker(informalWorker);

        workRepository.save(work);

    }
}
