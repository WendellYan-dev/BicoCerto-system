package com.example.apiBicoCerto.services.informalWorkerServices;


import com.example.apiBicoCerto.DTOs.InformalWorkerResponseDTO;
import com.example.apiBicoCerto.entities.InformalWorker;
import com.example.apiBicoCerto.repositories.InformalWorkerRepository;
import com.example.apiBicoCerto.utils.InformalWorkerSpecs;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ListInformalWorkerService {

    @Autowired
    private InformalWorkerRepository informalWorkerRepository;

    public List<InformalWorkerResponseDTO> listInformalWorker(String name,String local){

        Specification<InformalWorker> spec = InformalWorkerSpecs.filter(name,local);

        List<InformalWorker> workers = informalWorkerRepository.findAll(spec);

        return workers.stream()
                .map(this::toDTO)
                .toList();

    }

    private InformalWorkerResponseDTO toDTO(InformalWorker worker){
        return new InformalWorkerResponseDTO(
                worker.getUser().getUsername(),
                worker.getServiceCategory(),
                worker.getAboutMe(),
                worker.getLocalService()
        );

    }

}
