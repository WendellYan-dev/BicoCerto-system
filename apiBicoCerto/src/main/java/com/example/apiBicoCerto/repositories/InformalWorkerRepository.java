package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.Address;
import com.example.apiBicoCerto.entities.InformalWorker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformalWorkerRepository extends JpaRepository<InformalWorker, Integer> {
    InformalWorker findByUserId(Integer id);
}
