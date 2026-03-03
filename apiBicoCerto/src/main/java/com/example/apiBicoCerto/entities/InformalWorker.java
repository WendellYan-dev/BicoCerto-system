package com.example.apiBicoCerto.entities;

import com.example.apiBicoCerto.enums.ServiceCategories;
import com.example.apiBicoCerto.enums.UserStatus;
import com.example.apiBicoCerto.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Table(name = "informalworker")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InformalWorker{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informal_worker")
    private Integer idInformalWorker;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_category")
    private ServiceCategories serviceCategory;

    @Column(name = "about_me")
    private String aboutMe;

    @Column(name = "local_service")
    private String localService;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "informalWorker",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Work> works = new ArrayList<>();

}
