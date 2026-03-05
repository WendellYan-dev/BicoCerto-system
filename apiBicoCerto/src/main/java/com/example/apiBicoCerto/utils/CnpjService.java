package com.example.apiBicoCerto.utils;

public final class CnpjService {

    public static String normalize(String cnpj) {
        if (cnpj == null) {
            return null;
        }
        return cnpj.replaceAll("\\D", "");
    }

    public static String format(String cnpj) {
        if (cnpj == null) {
            return null;
        }

        return cnpj.replaceAll(
                "(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})",
                "$1.$2.$3/$4-$5"
        );
    }
}