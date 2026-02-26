package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByUserName(String userName);
    UserDetails findByEmail(String email);
}
