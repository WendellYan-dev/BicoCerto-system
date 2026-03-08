package com.example.apiBicoCerto.entities;

import com.example.apiBicoCerto.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Table(name = "booking")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_booking")
    private Integer id;

    @Column(name = "booking_date",nullable = false)
    private LocalDate bookingDate;

    @Column(name = "start_time",nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time",nullable = false)
    private LocalTime endTime;

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus bookingStatus;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_work", nullable = false)
    private Work work;

    @ManyToOne
    @JoinColumn(name = "id_informal_worker", nullable = false)
    private InformalWorker informalWorker;
}
