package com.example.apiBicoCerto.services.availabilityServices;

import com.example.apiBicoCerto.DTOs.ListAvailabilityDTO;
import com.example.apiBicoCerto.entities.Availability;
import com.example.apiBicoCerto.entities.InformalWorker;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.repositories.AvailabilityRepository;
import com.example.apiBicoCerto.repositories.InformalWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class ListAvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private InformalWorkerRepository informalWorkerRepository;

    public List<ListAvailabilityDTO> listAvailability(){

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
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Prestador não Cadastrado"
            );
        }
        List<Availability> availabilities =
                availabilityRepository.findByInformalWorker_IdInformalWorker(informalWorker.getIdInformalWorker());


        return availabilities.stream()
                .map(a -> new ListAvailabilityDTO(
                        a.getIdAvailability(),
                        a.getDayOfWeek(),
                        a.getStartTime(),
                        a.getEndTime()
                ))
                .toList();
    }
}
