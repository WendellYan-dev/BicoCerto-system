package com.example.apiBicoCerto.utils;

import com.example.apiBicoCerto.DTOs.HolidayDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class HolidayService {

    @Value("${holiday.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URL =
            "https://api.invertexto.com/v1/holidays/%d?token=%s";
    public boolean isHoliday(LocalDate date, String state, String city){

        try {

            int year = date.getYear();

            String url = String.format(BASE_URL, year, apiKey);

            if(state != null && !state.isBlank()){
                url += "&state=" + state;
            }

            if(city != null && !city.isBlank()){
                url += "&city=" + city;
            }

            ResponseEntity<HolidayDTO[]> response =
                    restTemplate.getForEntity(url, HolidayDTO[].class);

            if(response.getBody() == null){
                return false;
            }

            return Arrays.stream(response.getBody())
                    .anyMatch(h -> LocalDate.parse(h.getDate()).equals(date));

        } catch (Exception e){
            return false;
        }
    }
}