package com.alpha.exchangeRate;

import com.alpha.exchangeRate.exceptions.RateProviderException;
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
    public CurrencyRateService(@Qualifier("openExchangeRateProvider")RateProvider rateProvider)
    {
        this.rateProvider = rateProvider;
    }

    /**
     * This method represents changes of currency rate compared to yesterday
     * @param quoteCurrencyId - iso 4217 currency id represented as string
     * @param baseCurrencyId - iso 4217 currency id represented as string (base currency)
     * @return -1 if one has to pay less quoteCurrencyId to buy baseCurrencyId
     * 0 rate did not change
     * 1 if one has to pay more quoteCurrencyId to buy baseCurrencyId
     */
    public Integer getRecentRateDynamics(String quoteCurrencyId, String baseCurrencyId) throws RateProviderException
    {
        var currentRate = rateProvider.getCurrentCurrencyRate(quoteCurrencyId, baseCurrencyId);
        var yesterdayRate = rateProvider.getHistoricalCurrencyRate(quoteCurrencyId, baseCurrencyId,
                LocalDate.now().minusDays(1));

        return currentRate.compareTo(yesterdayRate);
    }
}
