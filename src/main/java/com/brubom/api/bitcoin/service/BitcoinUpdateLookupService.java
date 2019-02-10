package com.brubom.api.bitcoin.service;

/**
 * The background lookup service that updates database values for bitcoin
 * @see com.brubom.api.bitcoin.service.impl.BitcoinUpdateLookupServiceImpl
 */
public interface BitcoinUpdateLookupService {

    void UpdateBitcoinDatabase() throws InterruptedException;

}
