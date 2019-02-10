package com.brubom.api.bitcoin.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Helper class used to provide common datetime functions
 */

public class DateUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getStringFromLocalDate(LocalDate localDate){

        return localDate.format(formatter);

    }


    public static LocalDate getLocalDateFromString(String dateString){
        return LocalDate.parse(dateString, formatter);

    }

    public static LocalDateTime getLocalDateTimeDefaultFromString(String dateTimeString){

        return ZonedDateTime.parse(dateTimeString).toLocalDateTime();
    }

    public static LocalDateTime getLocalDateTimeZoneString(String dateTimeString, String zone){


        return LocalDateTime.ofInstant(Instant.ofEpochSecond( Integer.parseInt(dateTimeString)),
                ZoneId.of(zone));



    }

    public static String getStringFromLocalDateTimeDefault(LocalDateTime dateTime){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }


}
