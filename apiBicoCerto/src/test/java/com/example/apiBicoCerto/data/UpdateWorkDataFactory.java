package com.example.apiBicoCerto.data;

public class UpdateWorkDataFactory {

    public static String updateValidTitle(){
        long unique = System.currentTimeMillis() % 1000000;

        return "Festa de formatura %s".formatted(unique);
    }

    public static String updateValidDescription(){
        return "Serviço de qualidade, mais de 10 no ramo.";
    }

    public static String updateValidPrice(){
        return "200.00";
    }

    public static String updatePriceEqualZero(){
        return "0";
    }

    public static String updatePriceLessThanZero(){
        return "-20";
    }

    public static String updateEqualsTitle(){
        return "Teste de titulos iguais";
    }

    public static String updateEmptyTitle(){
        return "  ";
    }
}
