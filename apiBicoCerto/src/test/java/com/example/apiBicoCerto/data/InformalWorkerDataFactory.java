package com.example.apiBicoCerto.data;

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
}
