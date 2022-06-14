package com.alpha.exchangeRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

/**
 * Abstraction over different providers of currency exchange
 */
// This interface is unnecessary, but it might be useful if application will extend
public interface RateProvider
{
    BigDecimal getCurrentCurrencyRate(String fromCurrencyId, String toCurrencyId);
    BigDecimal getHistoricalCurrencyRate(String fromCurrencyId, String toCurrencyId, LocalDate date);
}
