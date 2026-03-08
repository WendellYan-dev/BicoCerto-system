package com.example.apiBicoCerto.services.bookingServices;

import com.example.apiBicoCerto.DTOs.RegisterBookingDTO;
import com.example.apiBicoCerto.entities.Booking;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.entities.Work;
import com.example.apiBicoCerto.enums.BookingStatus;
import com.example.apiBicoCerto.repositories.BookingRepository;
import com.example.apiBicoCerto.repositories.WorkRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@Transactional
public class RegisterBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private WorkRepository workRepository;

    public void registerBooking(RegisterBookingDTO registerBookingDTO){

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || Objects.requireNonNull(authentication.getPrincipal()).equals("anonymousUser")) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado. Faça login para continuar."
            );
        }

        User loggedUser = (User) authentication.getPrincipal();

        Work work = workRepository.findById(registerBookingDTO.idService())
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));

        Booking booking = new Booking();
        booking.setBookingDate(registerBookingDTO.bookingDate());
        booking.setBookingStatus(BookingStatus.SOLICITADO);
        booking.setStartTime(registerBookingDTO.startTime());
        booking.setEndTime(registerBookingDTO.endTime());
        booking.setNotes(registerBookingDTO.notes());
        booking.setUser(loggedUser);
        booking.setWork(work);
        booking.setInformalWorker(work.getInformalWorker());
    }
}
