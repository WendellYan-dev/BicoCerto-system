package com.example.apiBicoCerto.services.userServices;

import com.example.apiBicoCerto.DTOs.UpdateUserDTO;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.repositories.UserRepository;
import com.example.apiBicoCerto.utils.GenerateLinkService;
import com.example.apiBicoCerto.utils.VerificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.apiBicoCerto.exceptions.NotFoundException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Objects;

@Service
@Transactional
public class UpdateUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private GenerateLinkService generateLinkService;


    public void updateUser(UpdateUserDTO update, MultipartFile profilePhoto) throws IOException {
        //procura o usuário se ele já existe

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || Objects.equals(authentication.getPrincipal(), "anonymousUser")) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado. Faça login para continuar."
            );
        }

        User loggedUser = (User) authentication.getPrincipal();


        assert loggedUser != null;
        User user = userRepository.findById(loggedUser.getId()).orElseThrow(()-> new NotFoundException("Usuário não encontrado"));

        //verificações if para evitar sobrescrever no banco com valor nulo
        if(update.firstName()!=null){
            if(update.firstName().isBlank()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O primeironome não pode estar em branco");
            }
            user.setFirstName(update.firstName());
        }

        if(update.lastName()!=null){
            if(update.lastName().isBlank()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O sobrenome não pode estar em branco");
            }
            user.setLastName(update.lastName());
        }

        if (update.email() != null) {
            if(update.email().isBlank()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O email não pode estar em branco");
            }

            if(!update.email().equals(user.getEmail())){
                if (!verificationService.isValidEmail(update.email())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Formato de email inválido");
                }

                if (userRepository.existsByEmail(update.email())) {
                    throw new IllegalStateException("Email já em uso");
                }

                user.setEmail(update.email());
            }
        }


        if (update.password() != null) {

            if(update.password().isBlank()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A senha não pode estar em branco");
            }
            //o Spring faz o hash da senha informada usando o mesmo salt armazenado para comparar a senha atual com a que quero alterar
            if(passwordEncoder.matches(update.password(), user.getPassword())){
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"A nova senha não pode ser igual à senha atual");
            }

            if(!verificationService.isValidPassword(update.password())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha inválida! Senha deve ter pelo menos 8 dígitos");
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
            if (update.phoneNumber().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Telefone não pode ser vazio");
            }
            if(!verificationService.isValidPhone(update.phoneNumber())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de telefone inválido");
            }
            user.setPhoneNumber(update.phoneNumber());
        }

        if(update.status()!=null) {
            user.setStatus(update.status());
        }

        Boolean removePhoto = update.removePhoto();

        if(Boolean.TRUE.equals(removePhoto) && profilePhoto!=null && !profilePhoto.isEmpty()){
            throw new IllegalStateException("Não é permitido remover e enviar nova foto simultaneamente");
        }

        //para remover a imagem do banco
        if(Boolean.TRUE.equals(removePhoto)){
            user.setProfilePhoto(null);
        }

        //adicionar imagem atualizada no banco
        else if(profilePhoto!=null && !profilePhoto.isEmpty()){
            user.setProfilePhoto(generateLinkService.uploadImage(profilePhoto));
        }

        //caso fuja das 3 verificações anteriores,a foto atual continua no banco

        userRepository.save(user);

    }


}
