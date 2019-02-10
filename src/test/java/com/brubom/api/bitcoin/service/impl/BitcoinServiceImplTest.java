package com.brubom.api.bitcoin.service.impl;

import com.brubom.api.bitcoin.gateway.impl.BitcoinGatewayImpl;
import com.brubom.api.bitcoin.repository.dto.BitcoinRepositoryDTO;
import com.brubom.api.bitcoin.repository.impl.BitcoinRepositoryDAOImpl;
import com.brubom.api.bitcoin.service.dto.BitcoinServiceDTO;
import com.brubom.api.bitcoin.util.CurrentDateMock;
import com.brubom.api.bitcoin.util.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BitcoinServiceImplTest {


    @Autowired
    private BitcoinServiceImpl bitcoinService;

    @Mock
    private BitcoinRepositoryDAOImpl bitcoinRepositoryDAO;

    @Mock
    private BitcoinGatewayImpl bitcoinGateway;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void should_return_latest_rate_from_service(){

        bitcoinRepositoryDAO = new BitcoinRepositoryDAOImpl();
        bitcoinRepositoryDAO.setLatestRate(getLatestRateStub());


        BitcoinServiceDTO rate = bitcoinService.getLatestRate();

        assertNotNull(rate) ;

    }



    private BitcoinRepositoryDTO getLatestRateStub(){
        BitcoinRepositoryDTO bitcoinRepositoryDTO = new BitcoinRepositoryDTO();
        bitcoinRepositoryDTO.setRate(new BigDecimal(1000));
        bitcoinRepositoryDTO.setRateDate(CurrentDateMock.getMockCurrentDate());
        bitcoinRepositoryDTO.setLastUpdatedDateTime(
                LocalDateTime.of(CurrentDateMock.getMockCurrentDate(),
                        LocalTime.NOON));

        return bitcoinRepositoryDTO;
    }

    private List<BitcoinRepositoryDTO> getRepositoryHistoricalRatesStub(){


        BitcoinRepositoryDTO bitcoinRepositoryDTO = new BitcoinRepositoryDTO();
        bitcoinRepositoryDTO.setRate(new BigDecimal(1000));
        bitcoinRepositoryDTO.setRateDate(CurrentDateMock.getMockCurrentDate());
        bitcoinRepositoryDTO.setLastUpdatedDateTime(
                LocalDateTime.of(CurrentDateMock.getMockCurrentDate(),
                        LocalTime.NOON));

        bitcoinRepositoryDTO = new BitcoinRepositoryDTO();
        bitcoinRepositoryDTO.setRate(new BigDecimal(1000));
        bitcoinRepositoryDTO.setRateDate(CurrentDateMock.getMockCurrentDate().plusDays(-1));
        bitcoinRepositoryDTO.setLastUpdatedDateTime(
                LocalDateTime.of(CurrentDateMock.getMockCurrentDate().plusDays(-1),
                        LocalTime.NOON));

        bitcoinRepositoryDTO = new BitcoinRepositoryDTO();
        bitcoinRepositoryDTO.setRate(new BigDecimal(1000));
        bitcoinRepositoryDTO.setRateDate(CurrentDateMock.getMockCurrentDate().plusDays(-2));
        bitcoinRepositoryDTO.setLastUpdatedDateTime(
                LocalDateTime.of(CurrentDateMock.getMockCurrentDate().plusDays(-2),
                        LocalTime.NOON));

        return Arrays.asList(bitcoinRepositoryDTO, bitcoinRepositoryDTO, bitcoinRepositoryDTO);
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
