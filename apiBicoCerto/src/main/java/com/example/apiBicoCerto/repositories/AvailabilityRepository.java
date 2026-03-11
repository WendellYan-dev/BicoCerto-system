package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.Availability;
import com.example.apiBicoCerto.enums.DayOfWeekAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability,Integer> {
    boolean existsByInformalWorker_IdInformalWorkerAndDayOfWeekAndStartTimeLessThanAndEndTimeGreaterThan(
            Integer idInformalWorker,
            DayOfWeekAvailability dayOfWeek,
            LocalTime endTime,
            LocalTime startTime
    );

    Optional<Availability> findByIdAvailabilityAndInformalWorker_IdInformalWorker(
            Integer idAvailability,
            Integer idInformalWorker
    );
    List<Availability> findByInformalWorker_IdInformalWorkerAndDayOfWeek(
            Integer idInformalWorker,
            DayOfWeekAvailability dayOfWeek
    );
    boolean existsByInformalWorker_IdInformalWorkerAndDayOfWeekAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Integer idInformalWorker,
            DayOfWeekAvailability dayOfWeek,
            LocalTime startTime,
            LocalTime endTime
    );

}
