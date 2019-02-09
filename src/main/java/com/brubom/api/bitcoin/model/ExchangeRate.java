package com.brubom.api.bitcoin.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Model for exchange rates
 */
public class ExchangeRate {

    private BigDecimal rate;

    private LocalDate rateDate;

    public ExchangeRate(){

    }

    public ExchangeRate(LocalDate rateDate, BigDecimal rate) {
        this.rate = rate;
        this.rateDate = rateDate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDate getRateDate() {
        return rateDate;
    }

    public void setRateDate(LocalDate rateDate) {
        this.rateDate = rateDate;
    }
}
