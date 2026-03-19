package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work,Integer>, JpaSpecificationExecutor<Work> {
    boolean existsByTitleIgnoreCaseAndInformalWorker_IdInformalWorker(String title, Integer informalWorkerId);
    List<Work> findByTitleContainingIgnoreCase(String title);
}
