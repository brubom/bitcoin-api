package com.brubom.api.bitcoin.repository.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;
import com.brubom.api.bitcoin.repository.CurrencyRateDAO;
import com.brubom.api.bitcoin.repository.dto.CurrencyRateDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CurrencyRateDAOImpl implements CurrencyRateDAO {


    public static final String REDIS_HISTORY_TABLE_KEY = "history.exchange";
    private JedisPool jedis;

    public CurrencyRateDAOImpl(@Value("${spring.redis.host}") final String redisHost,
                               @Value("${spring.redis.port}") final int redisPort){

        jedis = new JedisPool( new JedisPoolConfig(),redisHost, redisPort);


    }

    /**
     * Get latest BitCoin/Dollar exchange rate
     *
     * @return
     */
    @Override
    public CurrencyRateDTO getLatestRate() {

        Tuple tuple = jedis.getResource().zrevrangeWithScores(REDIS_HISTORY_TABLE_KEY, 0, 1)
                .iterator().next();


        CurrencyRateDTO currencyRateDTO =
                new CurrencyRateDTO( String.valueOf(tuple.getScore()), tuple.getElement());

        return currencyRateDTO;

    }

    /**
     * Historical exchange rates for bitcoin/dollar in a range of dates
     *
     * @param initialDate Start date for exchange
     * @param endDate     Final exchange date
     * @return List of CurrencyRateDTO
     */
    @Override
    public List<CurrencyRateDTO> getHistoricalRates(LocalDate initialDate, LocalDate endDate) {

        Set<Tuple> elements = jedis.getResource().zrevrangeWithScores(
                REDIS_HISTORY_TABLE_KEY,
                initialDate.toEpochDay(),
                endDate.toEpochDay());


        List<CurrencyRateDTO> rates = new ArrayList<>();

        for(Tuple tuple: elements){
            CurrencyRateDTO currencyRateDTO =
                    new CurrencyRateDTO( String.valueOf(tuple.getScore()), tuple.getElement());

            rates.add(currencyRateDTO);
            System.out.println(tuple.getElement() + "-" + tuple.getScore());
        }

        return rates;


    }
}
