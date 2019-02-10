package com.brubom.api.bitcoin.repository.impl;

import com.brubom.api.bitcoin.repository.dto.BitcoinRepositoryDTO;
import com.brubom.api.bitcoin.util.CurrentDateMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BitcoinRepositoryDAOTest {


    private BitcoinRepositoryDAOImpl bitcoinRepositoryDAO;

    private RestTemplate restTemplate;



    @Test
    public void should_return_latest_rate_from_repository() throws IOException {

        BitcoinRepositoryDTO bitcoinRepositoryDTO = new BitcoinRepositoryDTO();
        bitcoinRepositoryDTO.setRate(new BigDecimal(1000));
        bitcoinRepositoryDTO.setRateDate(CurrentDateMock.getMockCurrentDate());
        bitcoinRepositoryDTO.setLastUpdatedDateTime(
                LocalDateTime.of(CurrentDateMock.getMockCurrentDate(),
                        LocalTime.NOON));

        bitcoinRepositoryDAO = new BitcoinRepositoryDAOImpl();
        bitcoinRepositoryDAO.setLatestRate(bitcoinRepositoryDTO);

        BitcoinRepositoryDTO rate = bitcoinRepositoryDAO.getLatestRate();
        assertNotNull(rate);


    }


    @Test
    public void should_return_range_rates_from_repository(){

        LocalDate startDate = CurrentDateMock.getMockCurrentDate();
        LocalDate endDate = CurrentDateMock.getMockCurrentDate().plusDays(-5);

        BitcoinRepositoryDTO bitcoinRepositoryDTO = new BitcoinRepositoryDTO();
        bitcoinRepositoryDTO.setRate(new BigDecimal(1000));
        bitcoinRepositoryDTO.setRateDate(CurrentDateMock.getMockCurrentDate());
        bitcoinRepositoryDTO.setLastUpdatedDateTime(
                LocalDateTime.of(CurrentDateMock.getMockCurrentDate(),
                        LocalTime.NOON));

        List<BitcoinRepositoryDTO> historicalRatesStub =
                Arrays.asList(bitcoinRepositoryDTO, bitcoinRepositoryDTO,
                        bitcoinRepositoryDTO);

        bitcoinRepositoryDAO = new BitcoinRepositoryDAOImpl();
        bitcoinRepositoryDAO.setHistoricalRates(historicalRatesStub);

        List<BitcoinRepositoryDTO> historicalRates =
                bitcoinRepositoryDAO.getHistoricalRates(startDate, endDate);


        assertNotNull(historicalRates);

    }

}
