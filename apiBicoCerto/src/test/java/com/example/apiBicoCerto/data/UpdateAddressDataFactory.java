package com.example.apiBicoCerto.data;

public class UpdateAddressDataFactory {

    public static String updateAddressValid(){

        return """
            {
              "postalCode": "49503456",
              "street": "Rua D",
              "neighborhood": "Centro",
              "state": "SE",
              "number": "1042",
              "complement": "Perto do Parque",
              "isPrimary": true
            }
        """;
    }

    public static String updateWithComplementNull(){
        return updateAddressValid().replace("\"Perto do Parque\""
                , "null");
    }

    public static String updateWithIsPrimaryFalse(){
        return updateAddressValid().replace("true"
                , "false");
    }

    public static String updateWithInvalidPostalCode(){
        return updateAddressValid().replace("49503456"
                , "495000");
    }

    public static String updateWithInvalidStreet(){
        return updateAddressValid().replace("\"Rua D\""
                , "null");
    }

    public static String updateWithInvalidNeighborhood(){
        return updateAddressValid().replace("\"Centro\""
                , "null");
    }

    public static String updateWithInvalidState(){
        return updateAddressValid().replace("\"SE\""
                , "null");
    }

    public static String updateWithInvalidNumber(){
        return updateAddressValid().replace("\"1042\""
                , "null");
    }

    public static String updateWithInvalidIsPrimary(){
        return updateAddressValid().replace("\"true\""
                , "null");
    }
}
