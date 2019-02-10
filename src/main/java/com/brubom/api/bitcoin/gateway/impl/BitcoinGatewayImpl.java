package com.brubom.api.bitcoin.gateway.impl;

import com.brubom.api.bitcoin.exception.GatewayException;
import com.brubom.api.bitcoin.gateway.BitcoinGateway;
import com.brubom.api.bitcoin.gateway.dto.BitcoinGatewayDTO;
import com.brubom.api.bitcoin.util.DateUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Implementation of BitcoinGateway
 */
@Component
public class BitcoinGatewayImpl implements BitcoinGateway {

    private static final Logger logger = LogManager.getLogger(BitcoinGatewayImpl.class);

    @Value("${spring.coindesk.rate.latest.endpoint}")
    private String bitcoinApiLatestRateEndpoint;

    @Value("${spring.coindesk.rate.historical.endpoint}")
    private String bitcoinApiHistoricalRateEndpoint;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DateUtils dateUtils;

    /**
     * Get latest rate from coindesk api
     *
     * @return a single CoindeskDTO
     */
    @Override
    public BitcoinGatewayDTO getLatestRate() throws GatewayException {

        logger.debug("Calling getLatestRate service on endpoint:" + bitcoinApiLatestRateEndpoint);
        ResponseEntity<String> response  = null;

        try {
            response = restTemplate.getForEntity(bitcoinApiLatestRateEndpoint , String.class);
        }catch (Exception ex){
            logger.error("Failed to call openweatherapi getLatestRate on " + bitcoinApiLatestRateEndpoint, ex);
            throw  new GatewayException("Error while consuming getLatestRate endpoin at: " +
                    bitcoinApiLatestRateEndpoint, ex);
        }

        try {
            return getLatestRatefromJson(response.getBody());
        }catch (Exception ex){
            logger.error("Failed to call json to DTO at: " + bitcoinApiLatestRateEndpoint, ex);
            throw  new GatewayException("Failed to call json to DTO at:: " +
                    bitcoinApiLatestRateEndpoint, ex);
        }


    }

    /**
     * Return rates from start to end date
     *
     * @param startDate start of historical values
     * @param endDate   end of historical values
     * @return a list of CoindeskDTO
     */
    @Override
    public List<BitcoinGatewayDTO> getHistoricalRates(LocalDate startDate, LocalDate endDate) throws GatewayException {

        logger.debug("Calling getHistoricalRates service on endpoint:" + bitcoinApiLatestRateEndpoint);
        ResponseEntity<String> response  = null;

        try {
            response = restTemplate.getForEntity(bitcoinApiLatestRateEndpoint , String.class);
        }catch (Exception ex){
            logger.error("Failed to call openweatherapi on " + bitcoinApiLatestRateEndpoint, ex);
            throw  new GatewayException("Failed to call openweatherapi on " + bitcoinApiLatestRateEndpoint, ex);
        }

        try {
            return getHistoricalRatesfromJson(response.getBody());
        }catch (Exception ex){
            logger.error("Failed to call json to DTO at: " + bitcoinApiLatestRateEndpoint, ex);
            throw  new GatewayException("Failed to call json to DTO at:: " +
                    bitcoinApiLatestRateEndpoint, ex);
        }

    }

    /**
     * Converts a latest bitcoin rate from bitcoindesk to a BitcoinGatewayDTO
     * @param jsonBody json from API
     * @returna single BitcoinGatewayDTO
     * @throws IOException
     */
    private BitcoinGatewayDTO getLatestRatefromJson(String jsonBody) throws IOException {


        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonBody);

        LocalDateTime lastUpdated =
                dateUtils.getLocalDateTimeDefaultFromString(root.path("time").path("updated").asText());

        LocalDate rateDate = lastUpdated.toLocalDate();

        BigDecimal rate =
                new BigDecimal(root.path("bpi").path("USD").path("rate_float").asDouble())
                        .setScale(2,RoundingMode.HALF_UP);

        BitcoinGatewayDTO bitcoinGatewayDTO = new BitcoinGatewayDTO();
        bitcoinGatewayDTO.setLastUpdatedDateTime(lastUpdated);
        bitcoinGatewayDTO.setRateDate(rateDate);
        bitcoinGatewayDTO.setRate(rate);

        return bitcoinGatewayDTO;

    }

    /**
     * Converting coindesk rate json history to a list of BitcoinGatewayDTO
     * @param jsonBody json from API
     * @return List of rates
     * @throws IOException
     */
    private List<BitcoinGatewayDTO> getHistoricalRatesfromJson(String jsonBody) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonBody);

        LocalDateTime lastUpdated =
                dateUtils.getLocalDateTimeDefaultFromString(root.path("time").path("updated").asText());

        List<BitcoinGatewayDTO> historicalRates = new ArrayList<>();
        for (Iterator<Map.Entry<String, JsonNode>> it = root.path("bpi").fields(); it.hasNext(); ) {

            Map.Entry<String, JsonNode> node = it.next();

            BitcoinGatewayDTO bitcoinGatewayDTO = new BitcoinGatewayDTO();

            bitcoinGatewayDTO.setLastUpdatedDateTime(lastUpdated);
            bitcoinGatewayDTO.setRateDate(
                    dateUtils.getLocalDateFromString(node.getKey()));

            bitcoinGatewayDTO.setRate(
                    new BigDecimal(node.getValue().asDouble())
                            .setScale(2, RoundingMode.HALF_UP));

            historicalRates.add(bitcoinGatewayDTO);

        }

        return historicalRates;

    }



}
