package com.example.apiBicoCerto.enums;

import lombok.Getter;

@Getter
public enum UserType {
    PRESTADOR("prestador"),
    CLIENTE("cliente");

    private final String userType;

    UserType(String usertype){
        this.userType = usertype;
    }

}