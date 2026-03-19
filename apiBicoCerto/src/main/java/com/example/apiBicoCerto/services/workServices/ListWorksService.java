package com.example.apiBicoCerto.services.workServices;

import com.example.apiBicoCerto.DTOs.RegisterWorkDTO;
import com.example.apiBicoCerto.DTOs.WorkDTO;
import com.example.apiBicoCerto.entities.Work;
import com.example.apiBicoCerto.repositories.WorkRepository;
import com.example.apiBicoCerto.utils.WorkSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ListWorksService {
    @Autowired
    private WorkRepository workRepository;

    public Page<WorkDTO> listWorks(String title, BigDecimal price, Pageable pageable){

        return workRepository
                .findAll(WorkSpecs.filter(title, price), pageable)
                .map(w -> new WorkDTO(
                        w.getTitle(),
                        w.getDescription(),
                        w.getPrice(),
                        null
                ));
    }
}
