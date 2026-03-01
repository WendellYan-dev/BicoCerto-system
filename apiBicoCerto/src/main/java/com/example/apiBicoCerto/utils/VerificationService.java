package com.example.apiBicoCerto.utils;

import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.time.LocalDate;
import java.util.Hashtable;

@Service
public class VerificationService {

    /* =========================
       CPF
       ========================= */

    public boolean isValidCpf(String cpf) {
        if (cpf == null) return false;

        // 2. Remove a formatação para realizar o cálculo dos dígitos verificadores
        String cleanedCpf = cpf.replaceAll("[^0-9]", "");

        // 3. Verifica se todos os dígitos são iguais (ex: 111.111.111-11), o que é inválido
        if (cleanedCpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int[] digits = cleanedCpf.chars().map(c -> c - '0').toArray();

            // Cálculo do 1º Dígito Verificador
            int sum1 = 0;
            for (int i = 0; i < 9; i++) sum1 += digits[i] * (10 - i);
            int checkDigit1 = 11 - (sum1 % 11);
            if (checkDigit1 > 9) checkDigit1 = 0;

            // Cálculo do 2º Dígito Verificador
            int sum2 = 0;
            for (int i = 0; i < 10; i++) sum2 += digits[i] * (11 - i);
            int checkDigit2 = 11 - (sum2 % 11);
            if (checkDigit2 > 9) checkDigit2 = 0;

            // Retorna verdadeiro se os dígitos calculados batem com os informados
            return checkDigit1 == digits[9] && checkDigit2 == digits[10];

        } catch (Exception e) {
            return false;
        }
    }

     /* =========================
      CNPJ
       ========================= */

    public boolean isValidCnpj(String cnpj) {
        if (cnpj == null) return false;

        // 1. Remove qualquer caractere que não seja número
        String cleanedCnpj = cnpj.replaceAll("[^0-9]", "");

        // 2. Verifica se possui 14 dígitos
        if (cleanedCnpj.length() != 14) return false;

        // 3. Verifica se todos os dígitos são iguais (ex: 11.111.111/1111-11), o que é inválido
        if (cleanedCnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] digits = cleanedCnpj.chars().map(c -> c - '0').toArray();

            // 4. Cálculo do 1º Dígito Verificador
            int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int sum1 = 0;
            for (int i = 0; i < 12; i++) {
                sum1 += digits[i] * weights1[i];
            }

            int checkDigit1 = 11 - (sum1 % 11);
            if (checkDigit1 >= 10) checkDigit1 = 0;

            // 5. Cálculo do 2º Dígito Verificador
            int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int sum2 = 0;
            for (int i = 0; i < 13; i++) {
                sum2 += digits[i] * weights2[i];
            }

            int checkDigit2 = 11 - (sum2 % 11);
            if (checkDigit2 >= 10) checkDigit2 = 0;

            // 6. Retorna verdadeiro se os dígitos calculados correspondem aos informados
            return checkDigit1 == digits[12] && checkDigit2 == digits[13];

        } catch (Exception e) {
            return false;
        }
    }

    /* =========================
       EMAIL
       ========================= */

    public boolean isValidEmail(String email) {
        if (email == null || !email.contains("@")) return false;

        String domain = email.substring(email.indexOf("@") + 1);

        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ctx = new InitialDirContext(env);

            Attributes attrs = ctx.getAttributes(domain, new String[]{"MX"});
            Attribute attr = attrs.get("MX");

            if (attr == null) {
                attrs = ctx.getAttributes(domain, new String[]{"A"});
                attr = attrs.get("A");
            }

            return attr != null && attr.size() > 0;

        } catch (NamingException e) {
            return false;
        }
    }

    /* =========================
       TELEFONE
       ========================= */

    public boolean isValidPhone(String phone) {
        if (phone == null) return false;

        String cleaned = phone.replaceAll("[^0-9]", "");
        return cleaned.length() == 10 || cleaned.length() == 11;
    }

    /* =========================
       DATA
       ========================= */

    public boolean isValidBirthDate(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now()) && date.isAfter(LocalDate.of(1900, 1, 1));
    }

    /* =========================
       TEXTO
       ========================= */

    public boolean isValidRequiredText(String text, int minimumLength) {
        return text != null && text.trim().length() >= minimumLength;
    }

    /* =========================
       UF / ESTADO
       ========================= */

    public boolean isValidState(String state) {
        return state != null && state.matches("[A-Z]{2}");
    }

    /* =========================
       CEP
       ========================= */

    public boolean isValidPostalCode(String postalCode) {
        if (postalCode == null) return false;
        // Aceita 12345678 ou 12345-678
        return postalCode.matches("\\d{8}") || postalCode.matches("\\d{5}-\\d{3}");
    }



    public boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        return true;
    }
}