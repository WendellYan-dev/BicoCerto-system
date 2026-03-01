package com.example.apiBicoCerto.services.addressServices;

import com.example.apiBicoCerto.DTOs.UpdateAddressDTO;
import com.example.apiBicoCerto.DTOs.UpdateAddressResponseDTO;
import com.example.apiBicoCerto.entities.Address;
import com.example.apiBicoCerto.exceptions.NotFoundException;
import com.example.apiBicoCerto.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UpdateAddressService {

    @Autowired
    AddressRepository addressRepository;

    public UpdateAddressResponseDTO updateAddress(Integer idAddress, UpdateAddressDTO update){

        Address address = addressRepository.findById(idAddress).orElseThrow(()-> new NotFoundException("Endereço não encontrado"));

        if(update.postalCode()!=null){
            address.setPostalCode(update.postalCode());
        }

        if(update.street()!=null){
            address.setStreet(update.street());
        }

        if(update.neighborhood()!=null){
            address.setNeighborhood(update.neighborhood());
        }

        if(update.state()!=null){
            address.setState(update.state());
        }

        if(update.complement()!=null){
            address.setComplement(update.complement());
        }

        if(update.isPrimary()!=null){
            address.setIsPrimary(update.isPrimary());
        }

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
