package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.Address;
import com.example.apiBicoCerto.entities.InformalWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InformalWorkerRepository extends JpaRepository<InformalWorker, Integer>, JpaSpecificationExecutor<InformalWorker> {
    InformalWorker findByUserId(Integer id);
    List<InformalWorker> findByUserUserNameContainingIgnoreCase(String userName);
}
