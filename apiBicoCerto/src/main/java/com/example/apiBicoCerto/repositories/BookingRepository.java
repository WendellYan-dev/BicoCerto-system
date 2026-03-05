package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
}
