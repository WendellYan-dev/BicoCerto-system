package com.example.apiBicoCerto.services.informalWorkerServices;

import com.example.apiBicoCerto.DTOs.UpResponseIformalWorkerDTO;
import com.example.apiBicoCerto.DTOs.UpdateInformalWorkerDTO;
import com.example.apiBicoCerto.entities.InformalWorker;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.exceptions.NotFoundException;
import com.example.apiBicoCerto.repositories.InformalWorkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@Transactional
public class UpdateInformalWorkerService {

    @Autowired
    InformalWorkerRepository informalWorkerRepository;

    public UpResponseIformalWorkerDTO updateProfile(UpdateInformalWorkerDTO update){

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
        if(update.serviceCategory()!=null){
            informalWorker.setServiceCategory(update.serviceCategory());
        }

        if(update.aboutMe()!=null){
            informalWorker.setAboutMe(update.aboutMe());
        }

        if(update.localService()!=null){
            informalWorker.setLocalService(update.localService());
        }

        informalWorkerRepository.save(informalWorker);

        return new UpResponseIformalWorkerDTO(
                informalWorker.getIdInformalWorker(),
                informalWorker.getUser().getUsername(),
                informalWorker.getServiceCategory(),
                informalWorker.getAboutMe(),
                informalWorker.getLocalService()
        );
    }

}
