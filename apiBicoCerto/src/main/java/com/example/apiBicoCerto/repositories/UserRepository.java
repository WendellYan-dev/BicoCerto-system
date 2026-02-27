package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository  extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
    User findByEmail(String email);
    User findByCpf(String cpf);
    User findByCnpj(String cnpf);
}
