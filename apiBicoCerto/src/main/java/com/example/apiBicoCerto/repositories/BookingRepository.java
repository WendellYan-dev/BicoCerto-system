package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.Booking;
import com.example.apiBicoCerto.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    boolean existsByInformalWorker_IdInformalWorkerAndBookingDateAndBookingStatusNotAndStartTimeLessThanAndEndTimeGreaterThan(
            Integer idInformalWorker,
            LocalDate bookingDate,
            BookingStatus bookingStatus,
            LocalTime endTime,
            LocalTime startTime
    );
}
