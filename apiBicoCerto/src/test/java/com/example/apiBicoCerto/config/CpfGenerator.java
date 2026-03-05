package com.example.apiBicoCerto.config;

import java.util.Random;

public class CpfGenerator {

    public static String generateCpf() {

        Random random = new Random();

        int[] cpf = new int[11];

        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10);
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += cpf[i] * (10 - i);
        }

        int remainder = sum % 11;
        cpf[9] = (remainder < 2) ? 0 : 11 - remainder;

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += cpf[i] * (11 - i);
        }

        remainder = sum % 11;
        cpf[10] = (remainder < 2) ? 0 : 11 - remainder;

        StringBuilder cpfString = new StringBuilder();

        for (int digit : cpf) {
            cpfString.append(digit);
        }

        return cpfString.toString();
    }
}
