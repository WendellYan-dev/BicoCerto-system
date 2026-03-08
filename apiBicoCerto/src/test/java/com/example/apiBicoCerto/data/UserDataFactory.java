package com.example.apiBicoCerto.data;

// Ver a questão da foto

import com.example.apiBicoCerto.config.CnpjGenerator;
import com.example.apiBicoCerto.config.CpfGenerator;

public class UserDataFactory {

    public static String userValid() {

        long unique = System.currentTimeMillis() % 100000;
        String cpf = CpfGenerator.generateCpf();

        return """
            {
                "userName": "Fulano%s",
                "email": "Fulano%s@email.com",
                "firstName": "Fulano",
                "lastName": "Silva",
                "phoneNumber": "79988123456",
                "birthDate": "2000-05-10",
                "password": "Senha@123",
                "cpf": "%s",
                "cnpj": null,
                "addresses": [
                    {
                      "postalCode": "49000000",
                      "street": "Rua A",
                      "neighborhood": "Centro",
                      "state": "SE",
                      "number": "100",
                      "complement": "Apto 2",
                      "isPrimary": true
                    }
                ]
            }
        """.formatted(unique, unique, cpf);
    }

    public static String userWithEmptyComplement() {
        return userValid().replace("\"complement\": \"Apto 2\""
                , "\"complement\": null");
    }

    public static String userWithUnderageBirthDate() {
        return userValid().replace("2000-05-10"
                , "2015-05-10");
    }

    public static String userWithTooOldBirthDate() {
        return userValid().replace("2000-05-10"
                , "1899-05-10");
    }

    public static String userWithInvalidPassword() {
        return userValid().replace("Senha@123"
                , "Senha@1");
    }

    public static String userWithInvalidPhone() {
        return userValid().replace("79988123456", "7998812345");
    }

    public static String userWithInvalidPostalCode() {
        return userValid().replace("49000000"
                , "490000");
    }

    public static String userWithNullPhone() {
        return userValid().replace("\"phoneNumber\": \"79988123456\""
                , "\"phoneNumber\": null");
    }

    // privacidade de telefone


    public static String userValidWithCnpj() {

        long unique = System.currentTimeMillis() % 100000;
        String cnpj = CnpjGenerator.generateCnpj();

        return """
            {
                "userName": "Fulano%s",
                "email": "Fulano%s@email.com",
                "firstName": "Fulano",
                "lastName": "Silva",
                "phoneNumber": "79988123456",
                "birthDate": "2000-05-10",
                "password": "Senha@123",
                "cpf": null,
                "cnpj": "%s",
                "addresses": [
                    {
                      "postalCode": "49000000",
                      "street": "Rua A",
                      "neighborhood": "Centro",
                      "state": "SE",
                      "number": "100",
                      "complement": "Apto 2",
                      "isPrimary": true
                    }
                ]
            }
        """.formatted(unique, unique, cnpj);
    }

    public static String userWithInvalidCpf() {

        long unique = System.currentTimeMillis() % 100000;

        return """
        {
            "userName": "Fulano%s",
            "email": "Fulano%s@email.com",
            "firstName": "Fulano",
            "lastName": "Silva",
            "phoneNumber": "79988123456",
            "birthDate": "2000-05-10",
            "password": "Senha@123",
            "cpf": "12345678900",
            "cnpj": null,
            "addresses": [
                {
                  "postalCode": "49000000",
                  "street": "Rua A",
                  "neighborhood": "Centro",
                  "state": "SE",
                  "number": "100",
                  "complement": "Apto 2",
                  "isPrimary": true
                }
            ]
        }
    """.formatted(unique, unique);
    }
}