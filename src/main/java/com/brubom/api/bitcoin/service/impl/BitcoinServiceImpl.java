package com.brubom.api.bitcoin.service.impl;

import com.brubom.api.bitcoin.service.BitcoinService;
import com.brubom.api.bitcoin.service.dto.BitcoinServiceDTO;

import java.util.List;

public class BitcoinServiceImpl implements BitcoinService {
    /**
     * get a single, latest rate convertion from bitcoin to dollar
     *
     * @return
     */
    @Override
    public BitcoinServiceDTO getLatestRate() {
        return null;
    }

    /**
     * get historical rates
     *
     * @param startDate start of historical data
     * @param endDate   end of historical data
     * @return list of BitcoinServiceDTO
     */
    @Override
    public List<BitcoinServiceDTO> getHistoricalRates(String startDate, String endDate) {
        return null;
    }
}
