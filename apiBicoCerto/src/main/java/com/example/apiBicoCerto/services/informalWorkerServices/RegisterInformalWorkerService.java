package com.example.apiBicoCerto.services.informalWorkerServices;


import com.example.apiBicoCerto.DTOs.InformalWorkerDTO;
import com.example.apiBicoCerto.DTOs.UserDTO;
import com.example.apiBicoCerto.entities.InformalWorker;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.repositories.AddressRepository;
import com.example.apiBicoCerto.repositories.InformalWorkerRepository;
import com.example.apiBicoCerto.repositories.UserRepository;
import com.example.apiBicoCerto.services.userServices.RegisterUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class RegisterInformalWorkerService {

    @Autowired
    private InformalWorkerRepository informalWorkerRepository;

    @Autowired
    private RegisterUserService registerUserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressRepository addressRepository;

    public void registerInformalWorker(InformalWorkerDTO informalWorkerDTO){

        InformalWorker informalWorker = new InformalWorker();
        User user;

        if(userRepository.findByCpf(informalWorkerDTO.cpf()) == null){
            user = registerUserService.registerUser(new UserDTO(
                    informalWorkerDTO.userName(),
                    informalWorkerDTO.email(),
                    informalWorkerDTO.firstName(),
                    informalWorkerDTO.lastName(),
                    informalWorkerDTO.phoneNumber(),
                    informalWorkerDTO.birthDate(),
                    informalWorkerDTO.password(),
                    informalWorkerDTO.cpf(),
                    informalWorkerDTO.cnpj(),
                    informalWorkerDTO.profilePhoto(),
                    informalWorkerDTO.registerDate(),
                    informalWorkerDTO.status(),
                    informalWorkerDTO.addresses(),
                    informalWorkerDTO.userType()
            ));
        }else{
            user = userRepository.findByCpf(informalWorkerDTO.cpf());
        }


        // ===== Campos específicos de InformalWorker =====

        if (informalWorkerDTO.serviceCategory() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Categoria de serviço é obrigatória"
            );
        }
        informalWorker.setServiceCategory(informalWorkerDTO.serviceCategory());
        informalWorker.setAboutMe(informalWorkerDTO.aboutMe());
        informalWorker.setLocalService(informalWorkerDTO.localService());

        // Como há @OneToOne com User
        informalWorker.setUser(user);

        informalWorkerRepository.save(informalWorker);

    }

}
