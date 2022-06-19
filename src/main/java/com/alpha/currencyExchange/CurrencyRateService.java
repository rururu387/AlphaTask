package com.alpha.currencyExchange;

import com.alpha.common.exceptions.InvalidParametersException;
import com.alpha.common.exceptions.UnreadableResponseException;
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
    public CurrencyRateService(@Qualifier("openExchangeRatesProvider") RateProvider rateProvider)
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
    public QuoteRateChange getRecentRateDynamics(String quoteCurrencyId, String baseCurrencyId)
            throws InvalidParametersException, UnreadableResponseException
    {
        var currentRate = rateProvider.getLatestCurrencyRate(quoteCurrencyId, baseCurrencyId);
        var yesterdayRate = rateProvider.getHistoricalCurrencyRate(quoteCurrencyId, baseCurrencyId,
                LocalDate.now().minusDays(1));

        // Cast comparison result (integer) to enum
        return QuoteRateChange.values()[currentRate.compareTo(yesterdayRate) + 1];
    }

    /**
     * Enum describes rate change of quote currency.
     * If one has to pay more quote currency than before to buy base currency then use FALL state.
     * If one has to pay the same amount of quote currency than before - STABLE state.
     * If one has to pay less quote currency - GAIN state.
     */
    @Getter
    public enum QuoteRateChange
    {
        FALL(-1),
        STABLE(0),
        GAIN(1);

        private int numVal;

        QuoteRateChange(int numVal)
        {
            this.numVal = numVal;
        }
    }
}
