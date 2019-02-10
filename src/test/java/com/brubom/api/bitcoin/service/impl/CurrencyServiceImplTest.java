package com.brubom.api.bitcoin.service.impl;

import com.brubom.api.bitcoin.model.ExchangeRate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import com.brubom.api.bitcoin.repository.BitcoinRepositoryDAO;
import com.brubom.api.bitcoin.repository.dto.CurrencyRateDTO;
import com.brubom.api.bitcoin.service.BitcoinService;
import com.brubom.api.bitcoin.util.CurrentDateMock;
import com.brubom.api.bitcoin.util.DateUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyServiceImplTest {


    @Autowired
    private BitcoinService currencyService;

    @Autowired
    private BitcoinRepositoryDAO currencyRateDAO;

    @Autowired
    private DateUtils dateUtils;


    @Test
    public void should_return_a_exchangerate(){

        CurrencyRateDTO exchangeRateDTO =
                new CurrencyRateDTO(dateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate()),"100.00");

        when(currencyRateDAO.getLatestRate()).thenReturn(exchangeRateDTO);
        ExchangeRate rate = currencyService.getLatestRate();



        assertNotNull(rate) ;

    }

    @Test
    public void should_return_a_list_exchangerate(){

        LocalDate startDate = CurrentDateMock.getMockCurrentDate();
        LocalDate endDate =CurrentDateMock.getMockCurrentDate().plusDays(5);

        when(currencyRateDAO.getHistoricalRates(startDate, endDate)).thenReturn(getMockExchangeRates());

        List<ExchangeRate> historicalRates =
                currencyService.getHistoricalRates(
                        dateUtils.getStringFromLocalDate(startDate),
                        dateUtils.getStringFromLocalDate(endDate));

        assertNotNull(historicalRates) ;

    }

    private List<CurrencyRateDTO> getMockExchangeRates(){

        return Arrays.asList(
                new CurrencyRateDTO( dateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate ()), "111.11"),
                new CurrencyRateDTO(dateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate().plusDays(1)), "222.11"),
                new CurrencyRateDTO(dateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate().plusDays(2)), "333.11"),
                new CurrencyRateDTO(dateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate().plusDays(3)), "444.11"),
                new CurrencyRateDTO(dateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate().plusDays(4)), "444.11"),
                new CurrencyRateDTO(dateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate().plusDays(5)), "555.11")
        ) ;

    }

}
