package com.brubom.api.bitcoin.service;

import com.brubom.api.bitcoin.service.dto.BitcoinServiceDTO;

import java.util.List;

/**
 * This is a service for bitcoin/dollar rates
 */
public interface BitcoinService {

    /**
     * get a single, latest rate convertion from bitcoin to dollar
     * @return
     */
    BitcoinServiceDTO getLatestRate();

    /**
     * get historical rates
     * @param startDate start of historical data
     * @param endDate end of historical data
     * @return list of BitcoinServiceDTO
     */
    List<BitcoinServiceDTO> getHistoricalRates(String startDate, String endDate);

}
