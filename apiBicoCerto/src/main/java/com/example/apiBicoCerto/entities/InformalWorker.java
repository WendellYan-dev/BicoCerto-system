package com.example.apiBicoCerto.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Table(name = "informalworker")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InformalWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informal_worker")
    private Integer id;

    @Column(name = "service_category",nullable = false)
    private String serviceCategory;

    @Column(name = "about_me",nullable = false)
    private String aboutMe;

    @Column(name = "local_service",nullable = false)
    private String localService;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "informalWorker", cascade = CascadeType.ALL)
    private List<Work> services;
}
