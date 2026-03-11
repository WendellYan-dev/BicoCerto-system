package com.example.apiBicoCerto.data;

public class UpdateUserDataFactory {

    public static String updateUserValid() {

        long unique = System.currentTimeMillis() % 100000;

        return """
            {
                "email": "novo%s@email.com",
                "firstName": "Novo",
                "lastName": "Nome",
                "phoneNumber": "79988123210",
                "birthDate": "2000-05-15",
                "password": "12345678",
                "removePhoto": false
            }
        """.formatted(unique);
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
        return """
            {
                "password": "Senha@123"
            }
        """;
    }

    public static String updateWithEmptyFirstName() {
        return updateUserValid().replace("Novo"
                , "");
    }

    public static String updateWithoutPassword() {
        return """
            {
                "email": "novoSemSenha@email.com",
                "firstName": "Sem",
                "lastName": "Senha",
                "phoneNumber": "79988123210",
                "birthDate": "2000-05-15"
            }
        """;
    }

    public static String updateWithoutFirstName() {
        return """
            {
                "email": "novoSemNome@email.com",
                "lastName": "Silva",
                "phoneNumber": "79988123210",
                "birthDate": "2000-05-15",
                "password": "12345678"
            }
        """;
    }
}
