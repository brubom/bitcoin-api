package com.brubom.api.bitcoin.controller;


import com.brubom.api.bitcoin.service.BitcoinService;
import com.brubom.api.bitcoin.service.dto.BitcoinServiceDTO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 */
@RestController
public class BitcoinController {

    private static final Logger logger = LogManager.getLogger(BitcoinController.class);

    private final String dateRegex = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";

    @Autowired
    private BitcoinService bitcoinService;

    @RequestMapping(
            value = "/historicalrates",
            params = { "start", "end" },
            method = GET)
    public List<BitcoinServiceDTO> getWeatherAverage(@RequestParam(value="start")
                                                   @Pattern(regexp = dateRegex)
                                                           String start,
                                                     @RequestParam(value="end")
                                               @Pattern(regexp = dateRegex)
                                                       String end)  {

        logger.debug("Getting historical rates on controller");
        return bitcoinService.getHistoricalRates(start, end);

    }

    @GetMapping("/latestrate")
    public BitcoinServiceDTO getLatestRate() {

        logger.debug("Getting latest rate on controller");
        return bitcoinService.getLatestRate();

    }
}
