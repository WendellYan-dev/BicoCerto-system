package com.example.apiBicoCerto.services.userServices;

import com.example.apiBicoCerto.DTOs.AddressDTO;
import com.example.apiBicoCerto.DTOs.UserDTO;
import com.example.apiBicoCerto.entities.Address;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.enums.UserStatus;
import com.example.apiBicoCerto.repositories.AddressRepository;
import com.example.apiBicoCerto.repositories.UserRepository;
import com.example.apiBicoCerto.utils.VerificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RegisterUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private VerificationService verificationService;

    public void registerUser(UserDTO dto) {

            User user = new User();

            if (dto.userName() != null) {
                if (userRepository.findByUserName(dto.userName()) != null) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Username já em uso!");
                }
                user.setUserName(dto.userName());
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username é obrigatório");
            }

            if(dto.email() != null){
                if(userRepository.findByEmail(dto.email()) != null){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado!");
                }
                if(verificationService.isValidEmail(dto.email())){
                    user.setEmail(dto.email());
                }else{
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de email inválido");
                }
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email é obrigatório");
            }

            if(dto.firstName() != null){
                user.setFirstName(dto.firstName());
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Primeiro nome é obrigatório");
            }

            if(dto.lastName() != null){
                user.setLastName(dto.lastName());
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sobrenome é obrigatório");
            }

            if(dto.phoneNumber() != null){
                if(!verificationService.isValidPhone(dto.phoneNumber())){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de telefone inválido");
                }
                user.setPhoneNumber(dto.phoneNumber());
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone é obrigatório");
            }


            if(dto.birthDate() != null){
                if(!verificationService.isValidBirthDate(dto.birthDate())){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data inválida");
                }
                user.setBirthDate(dto.birthDate());
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de nascimento é obrigatória");
            }

            if(dto.password() != null){
                String password = passwordEncoder.encode(dto.password());
                if(!verificationService.isValidPassword(dto.password())){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha inválida");
                }
                user.setPassword(password);
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha é obrigatória");
            }

            if(dto.cpf() != null && dto.cnpj() == null){

                if(!verificationService.isValidCpf(dto.cpf())){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF com formato inválido");
                }
                if(userRepository.findByCpf(dto.cpf()) != null){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado!");
                }
                user.setCpf(dto.cpf());
            }else if(dto.cpf() == null && dto.cnpj() != null){
                if(!verificationService.isValidCnpj(dto.cnpj())){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNPJ  com formato inválido");
                }
                if(userRepository.findByCnpj(dto.cnpj()) != null){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "CNPJ já cadastrado!");
                }
                user.setCnpj(dto.cnpj());
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você deve informar apenas CPF ou apenas CNPJ.");
            }



            user.setProfilePhoto(dto.profilePhoto());

        user.setUserType(dto.userType());

        user.setRegisterDate(java.time.LocalDate.now());
        user.setStatus(dto.status());

        if (dto.addresses() != null) {
            List<Address> addresses = dto.addresses().stream().map(addressDTO -> {

                Address address = new Address();
                address.setPostalCode(addressDTO.postalCode());
                address.setStreet(addressDTO.street());
                address.setNeighborhood(addressDTO.neighborhood());
                address.setState(addressDTO.state());
                address.setNumber(addressDTO.number());
                address.setComplement(addressDTO.complement());
                address.setPrimary(addressDTO.isPrimary());

                address.setUser(user);


                return address;

            }).toList();

            user.setAddresses(addresses);
        }
        userRepository.save(user);
    }
}