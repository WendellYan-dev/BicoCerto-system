package com.example.apiBicoCerto.services.userServices;

import com.example.apiBicoCerto.DTOs.UpdateUserDTO;
import com.example.apiBicoCerto.DTOs.UserDTO;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.repositories.UserRepository;
import com.example.apiBicoCerto.utils.VerificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.apiBicoCerto.exceptions.NotFoundException;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class UpdateUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationService verificationService;


    public void updateUser(Integer userId, UpdateUserDTO update){
        //procura o usuário se ele já existe
        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("Usuário não encontrado"));

        //verificações if para evitar sobrescrever no banco com valor nulo
        if(update.firstName()!=null){
            user.setFirstName(update.firstName());
        }

        if(update.lastName()!=null){
            user.setLastName(update.lastName());
        }

        if (update.email() != null &&
                !update.email().equals(user.getEmail())) {

            if (!verificationService.isValidEmail(update.email())) {
                throw new IllegalArgumentException("Formato de email inválido");
            }

            if (userRepository.existsByEmail(update.email())) {
                throw new IllegalStateException("Email já em uso!");
            }

            user.setEmail(update.email());
        }

        //o Spring faz o hash da senha informada usando o mesmo salt armazenado para comparar a senha atual com a que quero alterar
        if (update.password() != null && !passwordEncoder.matches(update.password(), user.getPassword())) {
            //aqui posso chamar algum método de retorno de senha já foi usada anteriormente,por exemplo
            if(!verificationService.isValidPassword(update.password())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha inválida");
            }
            String encoded = passwordEncoder.encode(update.password());
            user.setPassword(encoded);
        }

        if(update.birthDate()!=null) {
            if(!verificationService.isValidBirthDate(update.birthDate())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data inválida");
            }
            user.setBirthDate(update.birthDate());
        }

        if(update.phoneNumber()!=null) {
            if(!verificationService.isValidPhone(update.phoneNumber())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de telefone inválido");
            }
            user.setPhoneNumber(update.phoneNumber());
        }

        if(update.profilePhoto()!=null) {
            user.setProfilePhoto(update.profilePhoto());
        }

        if(update.status()!=null) {
            user.setStatus(update.status());
        }

        userRepository.save(user);

    }


}
