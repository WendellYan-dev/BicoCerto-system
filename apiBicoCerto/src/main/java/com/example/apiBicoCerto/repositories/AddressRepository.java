package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
