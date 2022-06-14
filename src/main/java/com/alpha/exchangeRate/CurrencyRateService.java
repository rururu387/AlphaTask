package com.alpha.exchangeRate;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Getter
public class CurrencyRateService
{
    RateProvider rateProvider;

    @Autowired
    public CurrencyRateService(/*@Qualifier("openExchangeRateProvider") */RateProvider rateProvider)
    {
        this.rateProvider = rateProvider;
    }

    /**
     *This method represents changes of currency rate compared to yesterday
     * @param fromCurrencyId - iso 4217 currency id represented as string
     * @param toCurrencyId - iso 4217 currency id represented as string (base currency)
     * @return -1 if one has to pay less fromCurrencyId to buy toCurrencyId
     * 0 rate did not change
     * 1 if one has to pay more fromCurrencyId to buy toCurrencyId
     */
    public Integer getRecentRateChange(String fromCurrencyId, String toCurrencyId)
    {
        var currentRate = rateProvider.getCurrentCurrencyRate(fromCurrencyId, toCurrencyId);
        var yesterdayRate = rateProvider.getHistoricalCurrencyRate(fromCurrencyId, toCurrencyId,
                LocalDate.now().minusDays(1));

        return currentRate.compareTo(yesterdayRate);
    }
}
