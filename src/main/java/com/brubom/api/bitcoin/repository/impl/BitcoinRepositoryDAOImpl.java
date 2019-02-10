package com.brubom.api.bitcoin.repository.impl;

import com.brubom.api.bitcoin.repository.BitcoinRepositoryDAO;
import com.brubom.api.bitcoin.repository.dto.BitcoinRepositoryDTO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class BitcoinRepositoryDAOImpl implements BitcoinRepositoryDAO {


    private static final Logger logger = LogManager.getLogger(BitcoinRepositoryDAOImpl.class);

    private static final Map<Long, BitcoinRepositoryDTO> latestRate =
            new ConcurrentHashMap<>(3);

    private static final Map<Long, BitcoinRepositoryDTO> historicalRates =
            new ConcurrentHashMap<>(365);




    /**
     * Get latest BitCoin/Dollar exchange rate
     *
     * @return single CurrencyRateDTO
     */
    @Override
    public BitcoinRepositoryDTO getLatestRate() {

        logger.debug("Getting latest rate");

        return latestRate.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getKey))
                .get().getValue();

    }

    /**
     * Historical exchange rates for bitcoin/dollar in a range of dates
     *
     * @param initialDate Start date for exchange
     * @param endDate     Final exchange date
     * @return List of CurrencyRateDTO
     */
    @Override
    public List<BitcoinRepositoryDTO> getHistoricalRates(LocalDate initialDate, LocalDate endDate) {


        return historicalRates.entrySet().stream()
                .filter(x -> x.getKey() >= initialDate.toEpochDay() && x.getKey() <= endDate.toEpochDay())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());


    }

    /**
     * Update latest rate in database for today
     *
     * @param latesBitcointRate latest rate
     */
    @Override
    public void setLatestRate(BitcoinRepositoryDTO latesBitcointRate) {

        latestRate.put(latesBitcointRate.getLastUpdatedDateTime().toEpochSecond(ZoneOffset.UTC), latesBitcointRate);


    }

    /**
     * Update historical rates in database
     *
     * @param historicalBitcoinRates historical rates
     */
    @Override
    public void setHistoricalRates(List<BitcoinRepositoryDTO> historicalBitcoinRates) {

        Map<Long, BitcoinRepositoryDTO> tempHistoricalRates =  new ConcurrentHashMap<>(365);


        historicalBitcoinRates.forEach(item ->{
            BitcoinRepositoryDTO bitcoinRepositoryDTO = new BitcoinRepositoryDTO();
            bitcoinRepositoryDTO.setRate(item.getRate());
            bitcoinRepositoryDTO.setRateDate(item.getRateDate());
            bitcoinRepositoryDTO.setLastUpdatedDateTime(item.getLastUpdatedDateTime());
            tempHistoricalRates.put( item.getLastUpdatedDateTime().toEpochSecond(ZoneOffset.UTC),
                    bitcoinRepositoryDTO );
        });

        historicalRates.clear();
        historicalRates.putAll(tempHistoricalRates);

    }
}
