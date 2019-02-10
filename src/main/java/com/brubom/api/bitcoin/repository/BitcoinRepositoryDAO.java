package com.brubom.api.bitcoin.repository;

import com.brubom.api.bitcoin.repository.dto.BitcoinRepositoryDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for ExchangeCurrency DAO
 */
public interface BitcoinRepositoryDAO {


    /**
     * Get latest BitCoin/Dollar exchange rate
     * @return
     */
    BitcoinRepositoryDTO getLatestRate();

    /**
     * Historical exchange rates for bitcoin/dollar in a range of dates
     * @param initialDate Start date for exchange
     * @param endDate Final exchange date
     * @return List of BitcoinRepositoryDTO
     */
    List<BitcoinRepositoryDTO> getHistoricalRates(LocalDate initialDate, LocalDate endDate);

    /**
     *
     * @param latestRate
     */
    void setLatestRate(BitcoinRepositoryDTO latestRate);

    /**
     * 
     * @param historicalRates
     */
    void setHistoricalRates(List<BitcoinRepositoryDTO> historicalRates);
}
