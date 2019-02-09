package com.brubom.api.bitcoin.service;

import com.brubom.api.bitcoin.model.ExchangeRate;

import java.util.List;

public interface CurrencyService {
    
    ExchangeRate getLatestRate();

    List<ExchangeRate> getHistoricalRates(String startDate, String endDate);

}
