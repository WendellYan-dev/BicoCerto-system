package com.example.apiBicoCerto.entities;

import com.example.apiBicoCerto.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Table(name = "availability")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_availability")
    private Integer idAvailability;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week",nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time",nullable = false)
    private LocalTime startTime;

    @Column (name = "end_time",nullable = false)
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "id_informal_worker",nullable = false)
    private InformalWorker informalWorker;


}
