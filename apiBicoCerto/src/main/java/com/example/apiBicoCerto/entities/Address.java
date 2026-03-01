package com.example.apiBicoCerto.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "address")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String number;

    @Column
    private String complement;

    //tive que mudar para primitivo para permitir comparações do tipo null
    @Column
    private Boolean isPrimary;

}
