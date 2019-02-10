package com.brubom.api.bitcoin.controller;

import com.brubom.api.bitcoin.service.BitcoinService;
import com.brubom.api.bitcoin.service.dto.BitcoinServiceDTO;
import com.brubom.api.bitcoin.service.impl.BitcoinServiceImpl;
import com.brubom.api.bitcoin.util.CurrentDateMock;
import com.brubom.api.bitcoin.util.DateUtils;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BitcoinController.class)
@TestPropertySource(properties = {
        "spring.bitcoin-api.rate.bitcoin.check.period=100",
        "spring.bitcoin-api.rate.bitcoin.number.days.update.historical.rates=365",
        "spring.coindesk.rate.latest.endpoint=https://api.coindesk.com/v1/bpi/currentprice/usd.json",
        "spring.coindesk.rate.historical.endpoint=https://api.coindesk.com/v1/bpi/historical/close.json"

})
public class BitcoinControllerTest extends TestCase {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BitcoinServiceImpl bitcoinService;


    @Test
    public void should_return_latest_rate_from_controller() throws Exception {

        BitcoinServiceDTO bitcoinServiceDTO = new BitcoinServiceDTO();
        bitcoinServiceDTO.setRate(new BigDecimal(1000).toString());
        bitcoinServiceDTO.setRateDate(
                DateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate()));
        bitcoinServiceDTO.setLastUpdatedDateTime(
                DateUtils.getStringFromLocalDateTimeDefault(LocalDateTime.of(CurrentDateMock.getMockCurrentDate(),
                        LocalTime.NOON)));


        when(bitcoinService.getLatestRate()).thenReturn(bitcoinServiceDTO);



        ResultActions resultActions =this.mockMvc.perform(
                get("/latestrate" ))
                .andDo(print());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.rate").value("1000"));


    }

    @Test
    public void should_return_historical_rates_from_controller() throws Exception {


        String startDate = DateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate().plusDays(-3));
        String endDate =
                DateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate());


        when(bitcoinService.getHistoricalRates(startDate,endDate)).thenReturn(getServiceHistoricalRatesStub());

        String url = "/historicalrates?start=" + startDate + "&end=" + endDate;

        ResultActions resultActions =this.mockMvc.perform(
                get(url))
                .andDo(print());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].rate").value("1000"));


    }

    private List<BitcoinServiceDTO> getServiceHistoricalRatesStub(){


        BitcoinServiceDTO bitcoinServiceDTO = new BitcoinServiceDTO();
        bitcoinServiceDTO.setRate(new BigDecimal(1000).toString());
        bitcoinServiceDTO.setRateDate(
                DateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate()));
        bitcoinServiceDTO.setLastUpdatedDateTime(
                DateUtils.getStringFromLocalDateTimeDefault(LocalDateTime.of(CurrentDateMock.getMockCurrentDate(),
                        LocalTime.NOON)));

        bitcoinServiceDTO = new BitcoinServiceDTO();
        bitcoinServiceDTO.setRate(new BigDecimal(1000).toString());
        bitcoinServiceDTO.setRateDate(
                DateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate().plusDays(-1)));
        bitcoinServiceDTO.setLastUpdatedDateTime(
                DateUtils.getStringFromLocalDateTimeDefault(
                        LocalDateTime.of(
                                CurrentDateMock.getMockCurrentDate().plusDays(-1),
                                LocalTime.NOON)));

        bitcoinServiceDTO = new BitcoinServiceDTO();
        bitcoinServiceDTO.setRate(new BigDecimal(1000).toString());
        bitcoinServiceDTO.setRateDate(
                DateUtils.getStringFromLocalDate(CurrentDateMock.getMockCurrentDate().plusDays(-2)));
        bitcoinServiceDTO.setLastUpdatedDateTime(
                DateUtils.getStringFromLocalDateTimeDefault(
                        LocalDateTime.of(
                                CurrentDateMock.getMockCurrentDate().plusDays(-2),
                                LocalTime.NOON)));

        return Arrays.asList(bitcoinServiceDTO, bitcoinServiceDTO, bitcoinServiceDTO);
    }




}
