package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByUserName(String userName);
    User findByCpf(String cpf);
    User findByCnpj(String cnpj);
    UserDetails findByEmail(String email);
    boolean existsByEmail(String email);
}