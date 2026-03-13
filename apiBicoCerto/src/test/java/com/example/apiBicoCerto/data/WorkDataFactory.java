package com.example.apiBicoCerto.data;

public class WorkDataFactory {

    public static String validTitleUnique(){
        return "Festa de casamento unica";
    }

    public static String validTitle(){
        return "Festa de casamento";
    }

    public static String validDescription(){
        return "Apenas finais de semana.";
    }

    public static String validPrice(){
        return "150.00";
    }

    public static String priceEqualZero(){
        return "0";
    }

    public static String priceLessThanZero(){
        return "-5";
    }

    public static String equalsTitle(){
        return "Teste de titulos iguais";
    }

    public static String emptyTitle(){
        return "  ";
    }
}
