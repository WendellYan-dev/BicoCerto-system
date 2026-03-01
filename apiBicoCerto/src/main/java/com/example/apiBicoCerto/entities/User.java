package com.example.apiBicoCerto.entities;

import com.example.apiBicoCerto.enums.UserStatus;
import com.example.apiBicoCerto.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Column(name = "user_name",nullable = false, unique = true)
    private String userName;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @Column(name = "birth_date",nullable = false)
    private LocalDate birthDate;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "cpf",unique = true)
    private String cpf;

    @Column(name = "cnpj",unique = true)
    private String cnpj;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "register_date",nullable = false)
    private LocalDate registerDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private UserStatus status;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Address> addresses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type",nullable = false)
    private UserType userType;

    @OneToOne(mappedBy = "user")
    private InformalWorker informalWorker;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.userType == UserType.PRESTADOR) return List.of(new SimpleGrantedAuthority("ROLE_PRESTADOR"), new SimpleGrantedAuthority("ROLE_CLIENTE"));
        else return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
