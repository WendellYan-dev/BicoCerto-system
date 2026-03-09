package com.example.apiBicoCerto.services.addressServices;

import com.example.apiBicoCerto.DTOs.UpdateAddressDTO;
import com.example.apiBicoCerto.DTOs.UpdateAddressResponseDTO;
import com.example.apiBicoCerto.entities.Address;
import com.example.apiBicoCerto.exceptions.NotFoundException;
import com.example.apiBicoCerto.repositories.AddressRepository;
import com.example.apiBicoCerto.utils.VerificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UpdateAddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    private VerificationService verificationService;

    public UpdateAddressResponseDTO updateAddress(Integer idAddress, UpdateAddressDTO update){

        Address address = addressRepository.findById(idAddress).orElseThrow(()-> new NotFoundException("Endereço não encontrado"));

        if (update.postalCode() == null || update.postalCode().isBlank()) {
            throw new IllegalArgumentException("CEP é obrigatório");
        }
        if(verificationService.isValidPostalCode(update.postalCode())){
            address.setPostalCode(update.postalCode());
        } else {
            throw new IllegalArgumentException("CEP inválido");
        }

        if (update.street() == null || update.street().isBlank()) {
            throw new IllegalArgumentException("Logradouro é obrigatório");
        }

        if (update.neighborhood() == null || update.neighborhood().isBlank()) {
            throw new IllegalArgumentException("Bairro é obrigatório");
        }

        if (update.state() == null || update.state().isBlank()) {
            throw new IllegalArgumentException("UF é obrigatório");
        }

        if(verificationService.isValidState(update.state())){
            address.setState(update.state());
        } else {
            throw new IllegalArgumentException("Estado inválido");
        }

        if (update.number() == null || update.number().isBlank()) {
            throw new IllegalArgumentException("Número é obrigatório");
        }

        if(verificationService.isValidNumber(update.number())){
            address.setNumber(update.number());
        }

        if (update.isPrimary() == null) {
            throw new IllegalArgumentException("Se é principal ou não é obrigatório");
        }


        address.setStreet(update.street());
        address.setNeighborhood(update.neighborhood());
        address.setComplement(update.complement());
        address.setIsPrimary(update.isPrimary());

        addressRepository.save(address);

        //criação de DTO response para exibir somente alguns dados que desejo e tornar o código mais limpo
        //além de evitar o loop ocasionado pela relação bidimensional entre user e address
        return new UpdateAddressResponseDTO(

                address.getId(),
                address.getPostalCode(),
                address.getNeighborhood(),
                address.getState(),
                address.getStreet(),
                address.getComplement(),
                address.getNumber(),
                address.getIsPrimary(),
                address.getUser().getId(),
                address.getUser().getUsername()
        );

    }

}
