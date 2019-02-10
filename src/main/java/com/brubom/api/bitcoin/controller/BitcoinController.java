package com.brubom.api.bitcoin.controller;


import com.brubom.api.bitcoin.service.BitcoinService;
import com.brubom.api.bitcoin.service.dto.BitcoinServiceDTO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.List;

/**
 *
 */
@RestController
public class BitcoinController {

    private static final Logger logger = LogManager.getLogger(BitcoinController.class);

    @Autowired
    private BitcoinService bitcoinService;

    @GetMapping("/historicalrates")
    public List<BitcoinServiceDTO> getWeatherAverage(@RequestParam(value="city")
                                                   @Pattern(regexp = "^[a-zA-Z ]*$")
                                                           String startDate,
                                                     @RequestParam(value="city")
                                               @Pattern(regexp = "^[a-zA-Z ]*$")
                                                       String endDate)  {

        logger.debug("Getting historical rates on controller");
        return bitcoinService.getHistoricalRates(startDate, endDate);

    }

    @GetMapping("/latestrate")
    public BitcoinServiceDTO getLatestRate() {

        logger.debug("Getting latest rate on controller");
        return bitcoinService.getLatestRate();

    }
}
