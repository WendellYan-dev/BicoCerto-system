package com.example.apiBicoCerto.enums;

import java.time.DayOfWeek;

public enum DayOfWeekAvailability {

    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static DayOfWeekAvailability fromJavaDayOfWeek(DayOfWeek dayOfWeek){
        return DayOfWeekAvailability.valueOf(dayOfWeek.name());
    }

}