package com.brubom.api.bitcoin.repository;

import com.brubom.api.bitcoin.repository.dto.CurrencyRateDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for ExchangeCurrency DAO
 */
public interface CurrencyRateDAO {


    /**
     * Get latest BitCoin/Dollar exchange rate
     * @return
     */
    CurrencyRateDTO getLatestRate();

    /**
     * Historical exchange rates for bitcoin/dollar in a range of dates
     * @param initialDate Start date for exchange
     * @param endDate Final exchange date
     * @return List of CurrencyRateDTO
     */
    List<CurrencyRateDTO> getHistoricalRates(LocalDate initialDate, LocalDate endDate);
}
