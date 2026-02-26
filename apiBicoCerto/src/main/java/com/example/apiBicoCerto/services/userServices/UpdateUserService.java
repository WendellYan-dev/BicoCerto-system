package com.example.apiBicoCerto.services.userServices;

import com.example.apiBicoCerto.DTOs.UpdateUserDTO;
import com.example.apiBicoCerto.DTOs.UserDTO;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.apiBicoCerto.exceptions.NotFoundException;

@Service
@Transactional
public class UpdateUserService {

    @Autowired
    UserRepository userRepository;

    public User updateUser(Integer userId, UpdateUserDTO update){
        //procura o usuário se ele já existe
        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("Usuário não encontrado"));

        //verificações if para evitar sobrescrever no banco com valor nulo
        if(update.firstName()!=null){
            user.setFirstName(update.firstName());
        }

        if(update.lastName()!=null){
            user.setLastName(update.lastName());
        }

        if(update.email()!=null && !user.getEmail().equals(update.email())){
            //aqui posso chamar algum método de verificação do email novo
            //posso também pôr um método de verificação de email já em uso no sistema
            user.setEmail(update.email());
        }

        if(update.birthDate()!=null) {
            user.setBirthDate(update.birthDate());
        }

        if(update.phoneNumber()!=null) {
            //aqui posso colocar um método também de verificação do novo telefone cadastrado
            user.setPhoneNumber(update.phoneNumber());
        }

        if(update.profilePhoto()!=null) {
            user.setProfilePhoto(update.profilePhoto());
        }

        if(update.status()!=null) {
            user.setStatus(update.status());
        }

        userRepository.save(user);

        return user;
    }


}
