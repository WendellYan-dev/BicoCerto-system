package com.example.apiBicoCerto.services.informalWorkerServices;


import com.example.apiBicoCerto.DTOs.InformalWorkerDTO;
import com.example.apiBicoCerto.DTOs.UserDTO;
import com.example.apiBicoCerto.entities.InformalWorker;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.enums.UserType;
import com.example.apiBicoCerto.repositories.AddressRepository;
import com.example.apiBicoCerto.repositories.InformalWorkerRepository;
import com.example.apiBicoCerto.repositories.UserRepository;
import com.example.apiBicoCerto.services.userServices.RegisterUserService;
import com.example.apiBicoCerto.utils.CnpjService;
import com.example.apiBicoCerto.utils.CpfService;
import com.example.apiBicoCerto.utils.GenerateLinkService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
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

    @Autowired
    private GenerateLinkService generateLinkService;


    public void registerInformalWorker(InformalWorkerDTO informalWorkerDTO, MultipartFile profilePhoto) throws IOException {

        InformalWorker informalWorker = new InformalWorker();
        User user = new User();
        String normalizedCpf = null;
        String normalizedCnpj = null;

        if (informalWorkerDTO.cpf() != null) {
            normalizedCpf = CpfService.normalize(informalWorkerDTO.cpf());
        }

        if (informalWorkerDTO.cnpj() != null) {
            normalizedCnpj = CnpjService.normalize(informalWorkerDTO.cnpj());
        }

        User existingUser = null;

        if (normalizedCpf != null) {
            existingUser = userRepository.findByCpf(normalizedCpf);
        } else if (normalizedCnpj != null) {
            existingUser = userRepository.findByCnpj(normalizedCnpj);
        }

        if (existingUser == null) {
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
                    informalWorkerDTO.addresses()

            ),profilePhoto);
        }else{
            if(informalWorkerDTO.cpf() != null){
                user = userRepository.findByCpf(CpfService.normalize(informalWorkerDTO.cpf()));
            }else if(informalWorkerDTO.cnpj() != null){
                user = userRepository.findByCnpj(CnpjService.normalize(informalWorkerDTO.cnpj()));
            }

            if (!user.getUsername().equals(informalWorkerDTO.userName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome de usuário não corresponde ao usuário.");
            }

            if (user.getEmail() != null && !user.getEmail().equals(informalWorkerDTO.email())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email não corresponde ao usuário.");
            }

            if (user.getFirstName() != null && !user.getFirstName().equals(informalWorkerDTO.firstName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Primeiro nome não corresponde ao usuário.");
            }

            if (user.getLastName() != null && !user.getLastName().equals(informalWorkerDTO.lastName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sobrenome não corresponde ao usuário.");
            }

            if (user.getPhoneNumber() != null && !user.getPhoneNumber().equals(informalWorkerDTO.phoneNumber())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone não corresponde ao usuário.");
            }

            if (user.getBirthDate() != null && !user.getBirthDate().equals(informalWorkerDTO.birthDate())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de nascimento não corresponde ao usuário.");
            }


            if (user.getCpf() != null && !user.getCpf().equals(CpfService.normalize(informalWorkerDTO.cpf()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF não corresponde ao usuário.");
            }

            if (user.getCnpj() != null && !user.getCnpj().equals(informalWorkerDTO.cnpj())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNPJ não corresponde ao usuário.");
            }

        }


        // ===== Campos específicos de InformalWorker =====

        if (informalWorkerDTO.serviceCategory() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Categoria de serviço inválida."
            );
        }
        informalWorker.setServiceCategory(informalWorkerDTO.serviceCategory());

        if (informalWorkerDTO.aboutMe() == null || informalWorkerDTO.aboutMe().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Descrição sobre o prestador é obrigatória."
            );
        }
        informalWorker.setAboutMe(informalWorkerDTO.aboutMe());

        if (informalWorkerDTO.localService() == null || informalWorkerDTO.localService().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Local de prestação de serviço é obrigatório."
            );
        }
        informalWorker.setLocalService(informalWorkerDTO.localService());

        // Como há @OneToOne com User
        if (user.getInformalWorker() != null) {
            throw new IllegalStateException("Usuário já é um prestador de serviço.");
        }
        user.setInformalWorker(informalWorker);
        user.setUserType(UserType.PRESTADOR);
        informalWorker.setUser(user);


        informalWorkerRepository.save(informalWorker);

    }

}
