package com.example.apiBicoCerto.services.availabilityServices;

import com.example.apiBicoCerto.DTOs.AvailabilityDTO;
import com.example.apiBicoCerto.DTOs.DeleteAvailabilityDTO;
import com.example.apiBicoCerto.entities.Availability;
import com.example.apiBicoCerto.entities.InformalWorker;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.exceptions.NotFoundException;
import com.example.apiBicoCerto.repositories.AvailabilityRepository;
import com.example.apiBicoCerto.repositories.InformalWorkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class DeleteAvailabilityService {

    @Autowired
    AvailabilityRepository availabilityRepository;

    @Autowired
    InformalWorkerRepository informalWorkerRepository;

    public void deleteAvailability(List<DeleteAvailabilityDTO> ids){

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


        assert loggedUser != null;

        InformalWorker informalWorker = informalWorkerRepository
                .findByUserId(loggedUser.getId());

        if (informalWorker == null) {
            throw new NotFoundException("Prestador de Serviços não encontrado");
        }

        for (DeleteAvailabilityDTO id : ids) {

            Integer availabilityId = id.id();

            Availability availability = availabilityRepository
                    .findByIdAvailabilityAndInformalWorker_IdInformalWorker(
                            availabilityId,
                            informalWorker.getIdInformalWorker()
                    )
                    .orElseThrow(() ->
                            new NotFoundException(
                                    "Disponibilidade não encontrada: " + availabilityId
                            )
                    );

            availabilityRepository.delete(availability);
        }

    }

}
