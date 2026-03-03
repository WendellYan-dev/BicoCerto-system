package com.example.apiBicoCerto.repositories;

import com.example.apiBicoCerto.entities.Availability;
import com.example.apiBicoCerto.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface AvailabilityRepository extends JpaRepository<Availability,Integer> {
    boolean existsByInformalWorker_IdInformalWorkerAndDayOfWeekAndStartTimeLessThanAndEndTimeGreaterThan(
            Integer idInformalWorker,
            DayOfWeek dayOfWeek,
            LocalTime endTime,
            LocalTime startTime
    );
}
