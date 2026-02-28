package com.example.apiBicoCerto.services.WorkServices;


import com.example.apiBicoCerto.DTOs.WorkDTO;
import com.example.apiBicoCerto.entities.InformalWorker;
import com.example.apiBicoCerto.entities.Work;
import com.example.apiBicoCerto.repositories.WorkRepository;
import com.example.apiBicoCerto.utils.GenerateLinkService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Transactional
public class RegisterWorkService {

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private GenerateLinkService generateLinkService;

    @Autowired
    private InformalWorkerRepository informalWorkerRepository;

    public void registerService(WorkDTO workDTO) throws IOException {
        String linkUrl = generateLinkService.uploadImage(workDTO.image());
        Work work = new Work();
        work.setDescription(workDTO.description());
        work.setTitle(workDTO.description());
        work.setPrice(workDTO.price());
        work.setUrlPhoto(linkUrl);

        InformalWorker informalWorker = informalWorkerRepository
                .findById(workDTO.id_informal_worker())
                .orElseThrow(() ->
                        new RuntimeException("Não existe nenhum prestador de serviço com esse id")
                );

        work.setInformalWorker(informalWorker);

        workRepository.save(work);

    }
}
