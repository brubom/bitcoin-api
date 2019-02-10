package com.brubom.api.bitcoin.gateway;

import com.brubom.api.bitcoin.exception.GatewayException;
import com.brubom.api.bitcoin.gateway.dto.BitcoinGatewayDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 *  This is an interface showing the gateway(outbound) service for coindesk api
 * @see com.brubom.api.bitcoin.gateway.impl.BitcoinGatewayImpl
 */
public interface BitcoinGateway {


    /**
     * Get latest rate from coindesk api
     * @return a single CoindeskDTO
     */
    BitcoinGatewayDTO getLatestRate() throws GatewayException;

    /**
     * Return rates from start to end date
     * @param startDate start of historical values
     * @param endDate end of historical values
     * @return a list of CoindeskDTO
     */
    List<BitcoinGatewayDTO> getHistoricalRates(LocalDate startDate, LocalDate endDate) throws GatewayException;

}
