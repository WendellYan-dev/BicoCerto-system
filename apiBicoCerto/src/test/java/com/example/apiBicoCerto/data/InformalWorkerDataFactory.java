package com.example.apiBicoCerto.data;

import com.example.apiBicoCerto.config.CpfGenerator;
import com.example.apiBicoCerto.config.UserTestDTO;

public class InformalWorkerDataFactory {

    public static String informalWorkerValid() {
        return UserDataFactory.userValid()
                .replace("Cliente", "InforWorker")
                .replace("}",
                        """
                            ,
                            "serviceCategory": "GARCOM",
                            "aboutMe": "Atuo como garcom em eventos a 8 anos",
                            "localService": "Aracaju - SE"
                        }
                        """);
    }

    public static String informalWorkerWithInvalidCategory() {
        return informalWorkerValid().replace("\"serviceCategory\": \"GARCOM\""
                , "\"serviceCategory\": \"ELETRICISTA\"");
    }

    public static String informalWorkerWithEmptyAboutMe() {
        return informalWorkerValid().replace("\"aboutMe\": \"Atuo como garcom em eventos a 8 anos\""
                , "\"aboutMe\": null");
    }

    public static String informalWorkerWithEmptyLocalService() {
        return informalWorkerValid().replace("\"localService\": \"Aracaju - SE\""
                , "\"localService\": null");
    }

    public static UserTestDTO informalWorkerValidComplex() {

        long unique = System.currentTimeMillis() % 100000;
        String cpf = CpfGenerator.generateCpf();

        String email = "worker" + unique + "@email.com";
        String password = "Senha@123";
        String userName = "worker" + unique;

        String json = """
            {
                "userName": "%s",
                "email": "%s",
                "firstName": "Joao",
                "lastName": "Silva",
                "phoneNumber": "79988123456",
                "birthDate": "1995-05-10",
                "password": "%s",
                "cpf": "%s",
                "cnpj": null,
                "serviceCategory": "GARCOM",
                "aboutMe": "Tenho 5 anos de experiência",
                "localService": "Aracaju - SE",
                "addresses": [
                    {
                      "postalCode": "49000000",
                      "street": "Rua A",
                      "neighborhood": "Centro",
                      "state": "SE",
                      "number": "100",
                      "complement": "Casa",
                      "isPrimary": true
                    }
                ]
            }
        """.formatted(userName, email, password, cpf);

        return new UserTestDTO(json, email, password);
    }
}
