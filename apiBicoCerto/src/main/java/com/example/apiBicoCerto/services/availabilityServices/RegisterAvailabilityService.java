package com.example.apiBicoCerto.services.availabilityServices;

import com.example.apiBicoCerto.DTOs.AvailabilityDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RegisterAvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private InformalWorkerRepository informalWorkerRepository;

    @Transactional
    public void registerAvailability(List<AvailabilityDTO> availabilityDTOList){

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
                    HttpStatus.UNAUTHORIZED,
                    "Apenas Prestadores podem cadastrar agenda."
            );
        }

        validateInternalConflicts(availabilityDTOList);

        List<Availability> availabilities = new ArrayList<>();

        for (AvailabilityDTO availabilityDTO : availabilityDTOList){

            //Valida Horário no banco;
            if (availabilityRepository.existsByInformalWorker_IdInformalWorkerAndDayOfWeekAndStartTimeLessThanAndEndTimeGreaterThan(
                            informalWorker.getIdInformalWorker(),
                            availabilityDTO.dayOfWeek(),
                            availabilityDTO.endTime(),
                            availabilityDTO.startTime()
                    )) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Conflito de horário para esse prestador."
                );
            }

            Availability availability = new Availability();
            availability.setDayOfWeek(availabilityDTO.dayOfWeek());
            availability.setStartTime(availabilityDTO.startTime());
            availability.setEndTime(availabilityDTO.endTime());
            availability.setInformalWorker(informalWorker);

            availabilities.add(availability);
        }
        availabilityRepository.saveAll(availabilities);
    }

    private void validateInternalConflicts(List<AvailabilityDTO> availabilityDTOList) {

        for (int i = 0; i < availabilityDTOList.size(); i++) {
            AvailabilityDTO current = availabilityDTOList.get(i);

            // valida horário básico
            if (!current.startTime().isBefore(current.endTime())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Horário inválido: hora inicial deve ser menor que a final."
                );
            }

            for (int j = i + 1; j < availabilityDTOList.size(); j++) {
                AvailabilityDTO other = availabilityDTOList.get(j);

                // só compara se for o mesmo dia
                if (current.dayOfWeek().equals(other.dayOfWeek())) {

                    boolean hasConflict =
                            current.startTime().isBefore(other.endTime()) &&
                                    current.endTime().isAfter(other.startTime());

                    if (hasConflict) {
                        throw new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Conflito de horários na própria requisição para o dia "
                                        + current.dayOfWeek()
                        );
                    }
                }
            }
        }
    }

}
