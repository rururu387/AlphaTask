package com.alpha.currencyExchange;

import com.alpha.common.exceptions.InvalidParametersException;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Abstraction over different providers of currency exchange
 */
// This interface is unnecessary, but it might be useful if application will extend
public interface RateProvider
{
    /**
     * This method presents current currency rate the same way as getCurrentCurrencyRate(...) does
     * @see RateProvider#getHistoricalCurrencyRate(String, String, LocalDate)
     */
    BigDecimal getLatestCurrencyRate(String quoteCurrencyId, String baseCurrencyId) throws InvalidParametersException;
    /**
     * This method is used
     * @param quoteCurrencyId iso 4217 currency id represented as string
     * @param baseCurrencyId iso 4217 currency id represented as string (base currency)
     * @return Amount of fromCurrency that one has to pay to get one coin of base currency, presented as BigDecimal
     */
    BigDecimal getHistoricalCurrencyRate(String quoteCurrencyId, String baseCurrencyId,
                                         LocalDate date) throws InvalidParametersException;
}
