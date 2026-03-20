package com.example.apiBicoCerto.services.informalWorkerServices;


import com.example.apiBicoCerto.DTOs.InformalWorkerResponseDTO;
import com.example.apiBicoCerto.entities.InformalWorker;
import com.example.apiBicoCerto.repositories.InformalWorkerRepository;
import com.example.apiBicoCerto.utils.InformalWorkerSpecs;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ListInformalWorkerService {

    @Autowired
    private InformalWorkerRepository informalWorkerRepository;

    public Page<InformalWorkerResponseDTO> listInformalWorker(String userName,String local,String serviceCategory,Integer offSet,Integer pageSize){

        Specification<InformalWorker> spec = InformalWorkerSpecs.filter(userName,local,serviceCategory);

        PageRequest pageRequest = PageRequest.of(offSet,pageSize);

        return informalWorkerRepository
                .findAll(spec,pageRequest)
                .map(this::toDTO);

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
