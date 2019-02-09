package com.brubom.api.bitcoin.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 */
@Component
public class DateUtils {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String getStringFromLocalDate(LocalDate localDate){

        return localDate.format(formatter);
    }


    public LocalDate getLocalDateFromString(String dateString){
        return LocalDate.parse(dateString, formatter);

    }



}
