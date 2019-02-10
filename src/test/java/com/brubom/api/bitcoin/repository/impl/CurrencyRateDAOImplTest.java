package com.brubom.api.bitcoin.repository.impl;

import com.brubom.api.bitcoin.repository.BitcoinRepositoryDAO;
import com.brubom.api.bitcoin.repository.dto.CurrencyRateDTO;
import com.brubom.api.bitcoin.util.CurrentDateMock;
import com.brubom.api.bitcoin.util.EmbededRedis;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyRateDAOImplTest {

    @Autowired
    private EmbededRedis embededRedisTestConfiguration;

    @Autowired
    private BitcoinRepositoryDAO currencyRateDAO;


    @Before
    public void setUp() throws Exception {
        embededRedisTestConfiguration.startRedis();
    }

    @Test
    public void should_return_latest_rate(){

        CurrencyRateDTO rate = currencyRateDAO.getLatestRate();
        assertNotNull(rate);

    }


    @Test
    public void should_return_range_rates(){

        LocalDate startDate = CurrentDateMock.getMockCurrentDate();
        LocalDate endDate = CurrentDateMock.getMockCurrentDate().plusDays(5);

        List<CurrencyRateDTO> historicalRates =
                currencyRateDAO.getHistoricalRates(startDate, endDate);

        assertNotNull(historicalRates);

    }

}
