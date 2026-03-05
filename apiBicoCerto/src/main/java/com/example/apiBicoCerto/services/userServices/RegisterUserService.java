package com.example.apiBicoCerto.services.userServices;

import com.example.apiBicoCerto.DTOs.AddressDTO;
import com.example.apiBicoCerto.DTOs.UserDTO;
import com.example.apiBicoCerto.entities.Address;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.enums.UserStatus;
import com.example.apiBicoCerto.repositories.AddressRepository;
import com.example.apiBicoCerto.repositories.UserRepository;
import com.example.apiBicoCerto.utils.CnpjService;
import com.example.apiBicoCerto.utils.CpfService;
import com.example.apiBicoCerto.utils.GenerateLinkService;
import com.example.apiBicoCerto.utils.VerificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
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

    @Autowired
    private GenerateLinkService generateLinkService;


    public User registerUser(UserDTO dto, MultipartFile profilePhoto) throws IOException {

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
                if((User) userRepository.findByEmail(dto.email()) != null){
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
                String cpf = CpfService.normalize(dto.cpf());
                if(!verificationService.isValidCpf(cpf)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF com formato inválido");
                }
                if(userRepository.findByCpf(cpf) != null){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado!");
                }
                user.setCpf(cpf);
            }else if(dto.cpf() == null && dto.cnpj() != null){
                String cnpj = CnpjService.normalize(dto.cnpj());
                if(!verificationService.isValidCnpj(cnpj)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNPJ  com formato inválido");
                }
                if(userRepository.findByCnpj(cnpj) != null){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "CNPJ já cadastrado!");
                }
                user.setCnpj(cnpj);
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você deve informar apenas CPF ou apenas CNPJ.");
            }


        user.setUserType(dto.userType());
        user.setRegisterDate(LocalDateTime.now().withNano(0));
        user.setStatus(dto.status());

        if (dto.addresses() != null) {
            List<Address> addresses = dto.addresses().stream().map(addressDTO -> {

                Address address = new Address();

                if (addressDTO.postalCode() != null) {
                    if(!verificationService.isValidPostalCode(addressDTO.postalCode())){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP  com formato inválido");
                    }
                    address.setPostalCode(addressDTO.postalCode());
                }else{
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP é obrigatório");
                }

                if (addressDTO.street() != null) {
                    address.setStreet(addressDTO.street());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rua é obrigatória");
                }

                if (addressDTO.neighborhood() != null) {
                    address.setNeighborhood(addressDTO.neighborhood());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bairro é obrigatório");
                }

                if (addressDTO.state() != null) {
                    address.setState(addressDTO.state());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado é obrigatório");
                }

                if (addressDTO.number() != null) {
                    address.setNumber(addressDTO.number());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Número é obrigatório");
                }

                address.setComplement(addressDTO.complement());

                address.setIsPrimary(addressDTO.isPrimary());

                address.setUser(user);

                return address;

            }).toList();

            user.setAddresses(addresses);
        }
        if (profilePhoto != null){
            user.setProfilePhoto(generateLinkService.uploadImage(profilePhoto));
        }
        userRepository.save(user);
        return user;
    }
}