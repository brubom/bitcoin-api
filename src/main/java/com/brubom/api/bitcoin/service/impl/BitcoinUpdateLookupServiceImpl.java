package com.brubom.api.bitcoin.service.impl;

import com.brubom.api.bitcoin.gateway.BitcoinGateway;
import com.brubom.api.bitcoin.gateway.dto.BitcoinGatewayDTO;
import com.brubom.api.bitcoin.repository.BitcoinRepositoryDAO;
import com.brubom.api.bitcoin.repository.dto.BitcoinRepositoryDTO;
import com.brubom.api.bitcoin.service.BitcoinUpdateLookupService;
import com.brubom.api.bitcoin.util.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource(value={"classpath:application.properties"})
public class BitcoinUpdateLookupServiceImpl implements BitcoinUpdateLookupService {


    private static final Logger logger = LogManager.getLogger(BitcoinUpdateLookupServiceImpl.class);

    @Autowired
    private BitcoinRepositoryDAO bitcoinRepositoryDAO;


    @Autowired
    private BitcoinGateway bitcoinGateway;

    @Value("${spring.bitcoin-api.rate.bitcoin.check.period}")
    private String bitcoinCheckPeriod;

    @Value("${spring.bitcoin-api.rate.bitcoin.number.days.update.historical.rates}")
    private String numberOfDaysToUpdateHistoricalRates;



    @Async
    @Override
    public void UpdateBitcoinDatabase() throws InterruptedException {

        logger.info("Updating bitcoin database");

        LocalDate startDate = LocalDate.now().minusDays(Integer.getInteger(numberOfDaysToUpdateHistoricalRates));
        LocalDate endDate = LocalDate.now();

        bitcoinRepositoryDAO.setHistoricalRates(
                fromGatewayToRepo(bitcoinGateway.getHistoricalRates(startDate, endDate)));

        bitcoinRepositoryDAO.setLatestRate(
                fromGatewayToRepo(bitcoinGateway.getLatestRate())
        );

        logger.info("Waiting :" + numberOfDaysToUpdateHistoricalRates + " for next update");
        Thread.sleep(Long.getLong(bitcoinCheckPeriod));

    }

    private List<BitcoinRepositoryDTO> fromGatewayToRepo(List<BitcoinGatewayDTO> historicalRates) {

        return historicalRates.stream()
                .map(bitcoinGatewayDTO -> {
                    BitcoinRepositoryDTO bitcoinRepositoryDTO = new BitcoinRepositoryDTO();
                    bitcoinRepositoryDTO.setRate(bitcoinGatewayDTO.getRate());

                    bitcoinRepositoryDTO.setLastUpdatedDateTime(bitcoinGatewayDTO.getLastUpdatedDateTime());

                    bitcoinRepositoryDTO.setRateDate(bitcoinGatewayDTO.getRateDate());

                    return bitcoinRepositoryDTO;
                })
                .collect(Collectors.toList());

    }

    private BitcoinRepositoryDTO fromGatewayToRepo(BitcoinGatewayDTO bitcoinGatewayDTO) {

        BitcoinRepositoryDTO bitcoinRepositoryDTO = new BitcoinRepositoryDTO();
        bitcoinRepositoryDTO.setRate(bitcoinGatewayDTO.getRate());

        bitcoinRepositoryDTO.setLastUpdatedDateTime(bitcoinGatewayDTO.getLastUpdatedDateTime());

        bitcoinRepositoryDTO.setRateDate(bitcoinGatewayDTO.getRateDate());

        return bitcoinRepositoryDTO;
    }
}
