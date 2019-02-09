package com.brubom.api.bitcoin.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Helper used to mock current date
 */
public class CurrentDateMock {

    public static LocalDate getMockCurrentDate(){

        Clock clock = Clock.fixed(Instant.parse("2019-02-08T10:15:30.00Z"), ZoneId.of("UTC"));
        return LocalDate.now(clock);
    }



}
