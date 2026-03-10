package com.example.apiBicoCerto.data;

public class UpdateInformalWorkerDataFactory {

    public static String updateInfoWorkerValid() {

        return """
            {
              "serviceCategory": "JARDINEIRO",
              "aboutMe": "Tenho 5 anos de experiência como jardineiro",
              "localService": "Itabaiana - SE"
            }
        """;
    }

    public static String updateWithInvalidServiceCaterory(){
        return updateInfoWorkerValid().replace("JARDINEIRO"
                ,"ELETRICISTA");
    }

    public static String updateWithNullServiceCaterory(){
        return updateInfoWorkerValid().replace("\"JARDINEIRO\""
                ,"null");
    }

    public static String updateWithEmptyAboutMe(){
        return updateInfoWorkerValid().replace("Tenho 5 anos de experiência como jardineiro"
                ,"  ");
    }

    public static String updateWithNullAboutMe(){
        return updateInfoWorkerValid().replace("\"Tenho 5 anos de experiência como jardineiro\""
                ,"null");
    }

    public static String updateWithEmptyLocalService(){
        return updateInfoWorkerValid().replace("Itabaiana - SE"
                ,"    ");
    }

    public static String updateWithNullLocalService(){
        return updateInfoWorkerValid().replace("\"Itabaiana - SE\""
                ,"null");
    }
}
