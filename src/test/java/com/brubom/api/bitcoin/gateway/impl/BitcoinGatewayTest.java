package com.brubom.api.bitcoin.gateway.impl;

import com.brubom.api.bitcoin.gateway.dto.BitcoinGatewayDTO;
import com.brubom.api.bitcoin.util.CurrentDateMock;
import com.brubom.api.bitcoin.util.DateUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BitcoinGatewayTest {

    @InjectMocks
    private BitcoinGatewayImpl bitcoinGateway;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_return_latest_rate_from_gateway() throws IOException {

        BitcoinGatewayDTO bitcoinGatewayDTO = new BitcoinGatewayDTO();
        bitcoinGatewayDTO.setRate(new BigDecimal(3632.76).setScale(2,RoundingMode.HALF_UP));
        bitcoinGatewayDTO.setRateDate(CurrentDateMock.getMockCurrentDate());
        bitcoinGatewayDTO.setLastUpdatedDateTime(
                LocalDateTime.of(CurrentDateMock.getMockCurrentDate(),
                        LocalTime.NOON));

        String jsonResponse = IOUtils.toString(
                this.getClass().getResourceAsStream("/latest.json"),
                "UTF-8"
        );

        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(
                Mockito.anyString(),
                ArgumentMatchers.any(Class.class)))
                .thenReturn(responseEntity);

        BitcoinGatewayDTO result =
                bitcoinGateway.getLatestRate();

        assertNotNull(result);
        assertEquals(result.getRate(), bitcoinGatewayDTO.getRate());

    }

    @Test
    public void should_return_historical_rates_from_gateway() throws IOException {

        BitcoinGatewayDTO bitcoinGatewayDTO = new BitcoinGatewayDTO();
        bitcoinGatewayDTO.setRate(new BigDecimal(3669.5825).setScale(2,RoundingMode.HALF_UP));
        bitcoinGatewayDTO.setRateDate(CurrentDateMock.getMockCurrentDate());
        bitcoinGatewayDTO.setLastUpdatedDateTime(
                LocalDateTime.of(CurrentDateMock.getMockCurrentDate(),
                        LocalTime.NOON));

        String jsonResponse = IOUtils.toString(
                this.getClass().getResourceAsStream("/historical.json"),
                "UTF-8"
        );

        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(
                Mockito.anyString(),
                ArgumentMatchers.any(Class.class)))
                .thenReturn(responseEntity);

        LocalDate startDate = CurrentDateMock.getMockCurrentDate();
        LocalDate endDate =CurrentDateMock.getMockCurrentDate().plusDays(-3);

        List<BitcoinGatewayDTO> result =
                bitcoinGateway.getHistoricalRates(startDate, endDate);

        assertNotNull(result);
        assertEquals(result.get(0).getRate(), bitcoinGatewayDTO.getRate());

    }
}
