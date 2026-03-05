package com.example.apiBicoCerto.services.authServices;

import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.repositories.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username){
        User user = (User) userRepository
                .findByUserNameOrEmail(username, username);

        if (user == null) {
            throw new UsernameNotFoundException("Login ou senha inválidos");
        }


        return user;
    }
}
