package com.brubom.api.bitcoin.controller;

import junit.framework.TestCase;
import com.brubom.api.bitcoin.model.ExchangeRate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.brubom.api.bitcoin.service.BitcoinService;
import com.brubom.api.bitcoin.util.CurrentDateMock;
import com.brubom.api.bitcoin.util.DateUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CurrencyRateController.class)
public class CurrencyRateControllerTest extends TestCase {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BitcoinService currencyService;

    @Autowired
    private DateUtils dateUtils;

    @Test
    public void should_return_current_rate() throws Exception {

        ExchangeRate exchangeRate =
                new ExchangeRate(CurrentDateMock.getMockCurrentDate(), new BigDecimal(100));

        when(currencyService.getLatestRate()).thenReturn(exchangeRate);

        ResultActions resultActions =this.mockMvc.perform(
                get("/exchangerate"))
                .andDo(print());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.rate").value("100"));


    }

    @Test
    public void should_return_historical_rates() throws Exception {

        ExchangeRate exchangeRate =
                new ExchangeRate(CurrentDateMock.getMockCurrentDate(), new BigDecimal(100));

        List<ExchangeRate> historicalRates = Arrays.asList(exchangeRate, exchangeRate, exchangeRate);

        String startDate = dateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate());
        String endDate =
                dateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate().plusDays(5));


        when(currencyService.getHistoricalRates(startDate,endDate)).thenReturn(historicalRates);

        String url = "/exchange/" + startDate + "/" + endDate;

        ResultActions resultActions =this.mockMvc.perform(
                get(url))
                .andDo(print());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.historicalRates[:1].rate").value("100"));


    }




}
