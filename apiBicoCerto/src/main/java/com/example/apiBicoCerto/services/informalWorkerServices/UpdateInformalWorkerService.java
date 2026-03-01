package com.example.apiBicoCerto.services.informalWorkerServices;

import com.example.apiBicoCerto.DTOs.UpResponseIformalWorkerDTO;
import com.example.apiBicoCerto.DTOs.UpdateInformalWorkerDTO;
import com.example.apiBicoCerto.entities.InformalWorker;
import com.example.apiBicoCerto.exceptions.NotFoundException;
import com.example.apiBicoCerto.repositories.InformalWorkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UpdateInformalWorkerService {

    @Autowired
    InformalWorkerRepository informalWorkerRepository;

    public UpResponseIformalWorkerDTO updateProfile(Integer id, UpdateInformalWorkerDTO update){

        InformalWorker informalWorker = informalWorkerRepository.findById(id).orElseThrow(()-> new NotFoundException("Prestador de Serviços não encontrado"));

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
