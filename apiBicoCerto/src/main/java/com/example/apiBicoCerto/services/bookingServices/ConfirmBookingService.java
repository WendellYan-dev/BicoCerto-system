package com.example.apiBicoCerto.services.bookingServices;

import com.example.apiBicoCerto.DTOs.ConfirmBookingDTO;
import com.example.apiBicoCerto.entities.Booking;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.enums.BookingStatus;
import com.example.apiBicoCerto.enums.ConfirmStatus;
import com.example.apiBicoCerto.repositories.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@Transactional
public class ConfirmBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public void confirmBooking(ConfirmBookingDTO confirmBookingDTO){
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

        // Buscar o work
        Booking booking = bookingRepository.findById(confirmBookingDTO.bookingId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Agendamento não encontrado"
                        )
                );

        // Verificar se o work pertence ao usuário logado
        assert loggedUser != null;
        if (!booking.getInformalWorker()
                .getUser()
                .getId()
                .equals(loggedUser.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não tem permissão para Confirmar este agendamento"
            );
        }
        if (!booking.getBookingStatus().equals(BookingStatus.SOLICITADO)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Este agendamento já foi processado"
            );
        }
        if (confirmBookingDTO.confirmStatus().equals(ConfirmStatus.CONFIRMAR)) {
            booking.setBookingStatus(BookingStatus.CONFIRMADO);
            bookingRepository.save(booking);
        } else if (confirmBookingDTO.confirmStatus().equals(ConfirmStatus.RECUSAR)) {
            bookingRepository.delete(booking);
        }
    }
}
