package com.example.apiBicoCerto.data;

public class AvailabilityDataFactory {

    public static String availabilityValid(){

        return """
            [
                {
                    "dayOfWeek": "MONDAY",
                    "startTime": "09:00",
                    "endTime": "12:00"
                }
            ]
        """;
    }

    public static String availabilityValidForTestConflict(){

        return """
            [
                {
                    "dayOfWeek": "FRIDAY",
                    "startTime": "14:00",
                    "endTime": "17:00"
                }
            ]
        """;
    }

    public static String availabilityInvalidConflict(){

        return """
            [
                {
                    "dayOfWeek": "FRIDAY",
                    "startTime": "15:30",
                    "endTime": "18:00"
                }
            ]
        """;
    }

    public static String availabilityInvalidHour(){

        return """
            [
                {
                    "dayOfWeek": "MONDAY",
                    "startTime": "15:00",
                    "endTime": "13:30"
                }
            ]
        """;
    }

    public static String availabilityInvalidWithFieldEmpty(){

        return """
            [
                {
                    "dayOfWeek": " ",
                    "startTime": "09:00",
                    "endTime": "12:00"
                }
            ]
        """;
    }

    public static String availabilityInvalidWithFieldNull(){

        return """
            [
                {
                    "dayOfWeek": " ",
                    "endTime": "12:00"
                }
            ]
        """;
    }

    public static String availabilityValidTwo(){

        return """
            [
                {
                    "dayOfWeek": "WEDNESDAY",
                    "startTime": "07:00",
                    "endTime": "09:00"
                },
                {
                    "dayOfWeek": "WEDNESDAY",
                    "startTime": "09:00",
                    "endTime": "12:00"
                }
            ]
        """;
    }

    public static String availabilityForDelete(){
        return """
            [
                {
                    "dayOfWeek": "SATURDAY",
                    "startTime": "08:00",
                    "endTime": "11:00"
                }
            ]
        """;
    }

    public static String deleteAvailabilityValid(int id){

        return """
            [
                {
                    "id": %s
                }
            ]
        """.formatted(id);
    }
}
