package com.example.apiBicoCerto.services.WorkServices;

import com.example.apiBicoCerto.DTOs.SearchWorkDTO;
import com.example.apiBicoCerto.entities.Work;
import com.example.apiBicoCerto.repositories.WorkRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class SearchWorkService {

    @Autowired
    private WorkRepository workRepository;

    public SearchWorkDTO searchWork(Integer workId) {

        Work work = workRepository.findById(workId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Serviço não encontrado"
                        )
                );

        return new SearchWorkDTO(
                work.getId(),
                work.getTitle(),
                work.getDescription(),
                work.getPrice(),
                work.getUrlPhoto()
        );
    }
}