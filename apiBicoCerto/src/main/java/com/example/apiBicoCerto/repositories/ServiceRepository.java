package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Integer> {
}
