package com.example.apiBicoCerto.data;

public class LoginDataFactory {

    public static String loginValid(){
        return """
            {
              "login": "igor123",
              "password": "Senha@123"
            }
            """;
    }

    public static String loginWithEmailValid(){
        return loginValid().replace("\"login\": \"igor123\""
                , "\"login\": \"igor.silva@email.com\"");
    }

    public static String loginWithUserInvalid(){
        return loginValid().replace("\"login\": \"igor123\""
                , "\"login\": \"igor12345\"");
    }

    public static String loginWithPasswordInvalid(){
        return loginValid().replace("\"password\": \"Senha@123\""
                , "\"password\": \"Senha@12345\"");
    }
}
