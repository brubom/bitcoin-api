package com.brubom.api.bitcoin.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Helper class used to provide common datetime functions
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

    public LocalDateTime getLocalDateTimeDefaultFromString(String dateTimeString){

        return getLocalDateTimeZoneString(dateTimeString, "UTC");
    }

    public LocalDateTime getLocalDateTimeZoneString(String dateTimeString, String zone){


        return LocalDateTime.ofInstant(Instant.ofEpochSecond( Integer.parseInt(dateTimeString)),
                ZoneId.of(zone));



    }


}
