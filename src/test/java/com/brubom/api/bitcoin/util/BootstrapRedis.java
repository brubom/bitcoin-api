package com.brubom.api.bitcoin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import redis.clients.jedis.Jedis;
import com.brubom.api.bitcoin.repository.dto.CurrencyRateDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Initializing redis with some data for testing
 */
@TestConfiguration
public class BootstrapRedis {

    private final String host;

    @Autowired
    private DateUtils dateUtils;

    public BootstrapRedis(@Value("${spring.redis.host}") final String host){
        this.host = host;

    }

    /**
     * Inserting some exchange rates in database
     */
    public void init() {

        //Connecting to Redis server on localhost
        Jedis jedis = new Jedis(host);
        System.out.println("Connection to server sucessfully");

        List<CurrencyRateDTO> rates = new ArrayList<>();
        for (int i = 0; i <= 5; i++){

            LocalDate date = CurrentDateMock.getMockCurrentDate().plusDays(i);
            CurrencyRateDTO currencyRateDTO =
                    new CurrencyRateDTO(dateUtils.getStringFromLocalDate(date),
                            ((100) * (i == 0 ? 1 : i)) + "");

            rates.add(currencyRateDTO);


        }

        //using zadd to added the elements ordered by date
        rates.forEach( r -> jedis.zadd(r.getRateDate(),
                dateUtils.getLocalDateFromString(r.getRateDate()).toEpochDay(),
                r.getRate()));


    }
}
