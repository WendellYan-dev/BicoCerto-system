package com.example.apiBicoCerto.data;

import com.example.apiBicoCerto.config.CpfGenerator;

public class UpdateUserDataFactory {

    public static String updateUserValid() {

        return """
            {
                "email": "novoEmail@email.com",
                "firstName": "Novo",
                "lastName": "Nome",
                "phoneNumber": "79988123210",
                "birthDate": "2000-05-15",
                "password": "12345678"
            }
        """;
    }

    public static String updateWithInvalidEmail() {
        return updateUserValid().replace("novoEmail@email.com"
                , "novoEmailemail.com");
    }

    public static String updateWithInvalidPhoneNumber() {
        return updateUserValid().replace("79988123210"
                , "7998812321");
    }

    public static String updateWithUnderageBirthDate() {
        return updateUserValid().replace("2000-05-15"
                , "2015-05-10");
    }

    public static String updateWithTooOldBirthDate() {
        return updateUserValid().replace("2000-05-15"
                , "1899-05-10");
    }

    public static String updateWithInvalidPassword() {
        return updateUserValid().replace("12345678"
                , "abcdefg");
    }

    public static String updateWithPasswordEquals() {
        return updateUserValid().replace("12345678"
                , "12345678");
    }

    public static String updateWithEmptyEmail() {
        return updateUserValid().replace("novoEmail@email.com", "");
    }

    public static String updateWithEmptyFirstName() {
        return updateUserValid().replace("Novo", "");
    }

    public static String updateWithNullPhoneNumber() {
        return updateUserValid().replace("\"79988123210\"", "null");
    }

    public static String updateWithNullBirthDate() {
        return updateUserValid().replace("\"2000-05-15\"", "null");
    }

    public static String updateTryingToChangeUsername() {

        return """
            {
                "userName": "novoUsername"
                "email": "novoEmail@email.com",
                "firstName": "Novo",
                "lastName": "Nome",
                "phoneNumber": "79988123210",
                "birthDate": "2000-05-15",
                "password": "12345678",
            }
        """;
    }

    public static String updateTryingToChangeCpf() {

        return """
            {
                "email": "novoEmail@email.com",
                "firstName": "Novo",
                "lastName": "Nome",
                "phoneNumber": "79988123210",
                "birthDate": "2000-05-15",
                "password": "12345678",
                "cpf": "%s"
            }
        """.formatted(CpfGenerator.generateCpf());
    }

    public static String updateWithoutPassword() {
        return """
            {
                "email": "novoEmail@email.com",
                "firstName": "Novo",
                "lastName": "Nome",
                "phoneNumber": "79988123210",
                "birthDate": "2000-05-15"
            }
        """;
    }

    public static String updateWithoutFirstName() {
        return """
            {
                "email": "novoEmail@email.com"
                "lastName": "Nome",
                "phoneNumber": "79988123210",
                "birthDate": "2000-05-15",
                "password": "12345678"
            }
        """;
    }
}
