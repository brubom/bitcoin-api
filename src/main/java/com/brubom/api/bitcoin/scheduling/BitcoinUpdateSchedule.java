package com.brubom.api.bitcoin.scheduling;

import com.brubom.api.bitcoin.gateway.BitcoinGateway;
import com.brubom.api.bitcoin.gateway.dto.BitcoinGatewayDTO;
import com.brubom.api.bitcoin.repository.BitcoinRepositoryDAO;
import com.brubom.api.bitcoin.repository.dto.BitcoinRepositoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@PropertySource(value={"classpath:application.properties"})
public class BitcoinUpdateSchedule {


    private static final Logger logger = LoggerFactory.getLogger(BitcoinUpdateSchedule.class);

    @Autowired
    private BitcoinRepositoryDAO bitcoinRepositoryDAO;


    @Autowired
    private BitcoinGateway bitcoinGateway;


    @Value("${spring.bitcoin-api.rate.bitcoin.number.days.update.historical.rates}")
    private int numberOfDaysToUpdateHistoricalRates;

    @PostConstruct
    private void init() {
       try{
           updateBitcoinDatabase();
       }catch (Exception ex){
           logger.error("Failed to stop scheduler", ex);
       }
    }

    @Scheduled(cron = "${spring.bitcoin-api.rate.bitcoin.check.period}")
    public void updateBitcoinDatabase() throws InterruptedException {

        logger.info("***********---Updating bitcoin database---***********");

        LocalDate startDate = LocalDate.now().minusDays((numberOfDaysToUpdateHistoricalRates == 0 ? 365 : numberOfDaysToUpdateHistoricalRates));
        LocalDate endDate = LocalDate.now();

        bitcoinRepositoryDAO.setHistoricalRates(
                fromGatewayToRepo(bitcoinGateway.getHistoricalRates(startDate, endDate)));

        bitcoinRepositoryDAO.setLatestRate(
                fromGatewayToRepo(bitcoinGateway.getLatestRate())
        );

        logger.info("Waiting :" + numberOfDaysToUpdateHistoricalRates + " for next update");


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
