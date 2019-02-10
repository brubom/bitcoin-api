package com.brubom.api.bitcoin.service.impl;

import com.brubom.api.bitcoin.exception.ServiceException;
import com.brubom.api.bitcoin.gateway.BitcoinGateway;
import com.brubom.api.bitcoin.gateway.dto.BitcoinGatewayDTO;
import com.brubom.api.bitcoin.repository.BitcoinRepositoryDAO;
import com.brubom.api.bitcoin.repository.dto.BitcoinRepositoryDTO;
import com.brubom.api.bitcoin.service.BitcoinService;
import com.brubom.api.bitcoin.service.dto.BitcoinServiceDTO;
import com.brubom.api.bitcoin.util.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BitcoinServiceImpl implements BitcoinService {

    private static final Logger logger = LogManager.getLogger(BitcoinServiceImpl.class);

    @Autowired
    private BitcoinRepositoryDAO bitcoinRepositoryDAO;

    @Autowired
    private BitcoinGateway bitcoinGateway;



    /**
     * get a single, latest rate convertion from bitcoin to dollar
     *
     * @return
     */
    @Override
    public BitcoinServiceDTO getLatestRate() {

        try {
            return fromRepoToServiceDTO(bitcoinRepositoryDAO.getLatestRate());
        }catch (Exception ex){
            logger.error("Failed to get latest rate on service", ex);
            throw  new ServiceException("Failed to get latest rate on service", ex);
        }
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

        try {
            LocalDate start = DateUtils.getLocalDateFromString(startDate);
            LocalDate end = DateUtils.getLocalDateFromString(endDate);

            List<BitcoinRepositoryDTO> historyFromRepo =
                    bitcoinRepositoryDAO.getHistoricalRates(start, end);

            if(historyFromRepo.size() > 0)
                return fromRepoToServiceDTO(historyFromRepo);


            return fromGatewayToServiceDTO(bitcoinGateway.getHistoricalRates(start, end));

        }catch (Exception ex){
            logger.error("Failed to get historical rates on service", ex);
            throw  new ServiceException("Failed to get historical rates on service", ex);
        }

    }

    private List<BitcoinServiceDTO> fromGatewayToServiceDTO(List<BitcoinGatewayDTO> historicalRates) {

        return historicalRates.stream()
                .map(bitcoinGatewayDTO -> {
                    BitcoinServiceDTO bitcoinServiceDTO = new BitcoinServiceDTO();
                    bitcoinServiceDTO.setRate(bitcoinGatewayDTO.getRate().toString());

                    bitcoinServiceDTO.setLastUpdatedDateTime(
                            DateUtils.getStringFromLocalDateTimeDefault(bitcoinGatewayDTO.getLastUpdatedDateTime()));

                    bitcoinServiceDTO.setRateDate(
                            DateUtils.getStringFromLocalDate(bitcoinGatewayDTO.getRateDate()));

                    return bitcoinServiceDTO;
                })
                .collect(Collectors.toList());
    }

    private List<BitcoinServiceDTO> fromRepoToServiceDTO(List<BitcoinRepositoryDTO> historyFromRepo) {
        return historyFromRepo.stream()
                .map(bitcoinGatewayDTO -> {
                    BitcoinServiceDTO bitcoinServiceDTO = new BitcoinServiceDTO();
                    bitcoinServiceDTO.setRate(bitcoinGatewayDTO.getRate().toString());

                    bitcoinServiceDTO.setLastUpdatedDateTime(
                            DateUtils.getStringFromLocalDateTimeDefault(bitcoinGatewayDTO.getLastUpdatedDateTime()));

                    bitcoinServiceDTO.setRateDate(
                            DateUtils.getStringFromLocalDate(bitcoinGatewayDTO.getRateDate()));

                    return bitcoinServiceDTO;
                })
                .collect(Collectors.toList());
    }

    private BitcoinServiceDTO fromRepoToServiceDTO(BitcoinRepositoryDTO historyFromRepo) {

        BitcoinServiceDTO bitcoinServiceDTO = new BitcoinServiceDTO();
        bitcoinServiceDTO.setRate(historyFromRepo.getRate().toString());

        bitcoinServiceDTO.setLastUpdatedDateTime(
                DateUtils.getStringFromLocalDateTimeDefault(historyFromRepo.getLastUpdatedDateTime()));

        bitcoinServiceDTO.setRateDate(
                DateUtils.getStringFromLocalDate(historyFromRepo.getRateDate()));

        return bitcoinServiceDTO;
    }

}
