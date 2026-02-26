package com.example.apiBicoCerto.services.userServices;

import com.example.apiBicoCerto.DTOs.UserDTO;
import com.example.apiBicoCerto.entities.Address;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.repositories.AddressRepository;
import com.example.apiBicoCerto.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void registerUser(UserDTO dto) {

        User user = new User();

        String password = passwordEncoder.encode(dto.password());
        user.setUserName(dto.userName());
        user.setEmail(dto.email());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setPhoneNumber(dto.phoneNumber());
        user.setBirthDate(dto.birthDate());
        user.setPassword(password);
        user.setCpf(dto.cpf());
        user.setCnpj(dto.cnpj());
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