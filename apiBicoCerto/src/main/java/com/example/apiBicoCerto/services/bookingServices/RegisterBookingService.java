package com.example.apiBicoCerto.services.bookingServices;

import com.example.apiBicoCerto.DTOs.RegisterBookingDTO;
import com.example.apiBicoCerto.entities.Booking;
import com.example.apiBicoCerto.entities.User;
import com.example.apiBicoCerto.entities.Work;
import com.example.apiBicoCerto.enums.BookingStatus;
import com.example.apiBicoCerto.enums.DayOfWeekAvailability;
import com.example.apiBicoCerto.repositories.AvailabilityRepository;
import com.example.apiBicoCerto.repositories.BookingRepository;
import com.example.apiBicoCerto.repositories.WorkRepository;
import com.example.apiBicoCerto.utils.HolidayService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Objects;

@Service
@Transactional
public class RegisterBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private AvailabilityRepository availabilityRepository;

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

        if (Objects.equals(loggedUser.getId(), work.getInformalWorker().getUser().getId())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possível agendar o seu próprio serviço"
            );
        }

        if(registerBookingDTO.bookingDate().isBefore(LocalDate.now())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possível agendar em datas passadas"
            );
        }

        if (!work.getInformalWorker().getWorksOnHoliday()){
            //Pode procurar por feriados locais caso queira mudar posteriormente,precisa apenas preencher os campos nulos.
            if (holidayService.isHoliday(registerBookingDTO.bookingDate(),null,null)){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O Prestador não trabalha em feriados"
                );
            }
        }

        if(!registerBookingDTO.startTime().isBefore(registerBookingDTO.endTime())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Horário inicial deve ser antes do horário final"
            );
        }

        DayOfWeekAvailability dayOfWeekAvailability =
                DayOfWeekAvailability.fromJavaDayOfWeek(
                        registerBookingDTO.bookingDate().getDayOfWeek()
                );
        if (!availabilityRepository.existsByInformalWorker_IdInformalWorkerAndDayOfWeekAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                work.getInformalWorker().getIdInformalWorker(),
                dayOfWeekAvailability,
                registerBookingDTO.startTime(),
                registerBookingDTO.endTime()
        )){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Horário fora da disponibilidade do prestador"
            );
        }

        boolean conflito = bookingRepository
                .existsByInformalWorker_IdInformalWorkerAndBookingDateAndBookingStatusNotAndStartTimeLessThanAndEndTimeGreaterThan(
                        work.getInformalWorker().getIdInformalWorker(),
                        registerBookingDTO.bookingDate(),
                        BookingStatus.CANCELADO,
                        registerBookingDTO.endTime(),
                        registerBookingDTO.startTime()
                );

        if (conflito) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Já existe um agendamento nesse horário"
            );
        }

        Booking booking = new Booking();
        booking.setBookingDate(registerBookingDTO.bookingDate());
        booking.setBookingStatus(BookingStatus.SOLICITADO);
        booking.setStartTime(registerBookingDTO.startTime());
        booking.setEndTime(registerBookingDTO.endTime());
        booking.setNotes(registerBookingDTO.notes());
        booking.setUser(loggedUser);
        booking.setWork(work);
        booking.setInformalWorker(work.getInformalWorker());

        bookingRepository.save(booking);
    }
}
