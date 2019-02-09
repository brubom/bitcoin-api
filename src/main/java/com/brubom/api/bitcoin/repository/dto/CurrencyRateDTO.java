package com.brubom.api.bitcoin.repository.dto;

public class CurrencyRateDTO {

    private String rateDate;

    private String rate;

    public CurrencyRateDTO(String rateDate, String rate) {
        this.rate = rate;
        this.rateDate = rateDate;
    }

    public String getRateDate() {
        return rateDate;
    }

    public void setRateDate(String rateDate) {
        this.rateDate = rateDate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
