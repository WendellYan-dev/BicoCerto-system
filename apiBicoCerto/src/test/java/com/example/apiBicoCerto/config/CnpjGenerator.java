package com.example.apiBicoCerto.config;

import java.util.Random;

public class CnpjGenerator {
    public static String generateCnpj() {

        Random random = new Random();
        int[] cnpj = new int[14];

        // primeiros 12 números aleatórios
        for (int i = 0; i < 12; i++) {
            cnpj[i] = random.nextInt(10);
        }

        int[] weight1 = {5,4,3,2,9,8,7,6,5,4,3,2};
        int[] weight2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += cnpj[i] * weight1[i];
        }

        int remainder = sum % 11;
        cnpj[12] = (remainder < 2) ? 0 : 11 - remainder;

        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += cnpj[i] * weight2[i];
        }

        remainder = sum % 11;
        cnpj[13] = (remainder < 2) ? 0 : 11 - remainder;

        StringBuilder cnpjString = new StringBuilder();

        for (int digit : cnpj) {
            cnpjString.append(digit);
        }

        return cnpjString.toString();
    }
}
