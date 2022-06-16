package com.alpha.exchangeRate;

import com.alpha.common.exceptions.InvalidParametersException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Getter
public class CurrencyRateService
{
    RateProvider rateProvider;

    @Autowired
    public CurrencyRateService(@Qualifier("openExchangeRateProvider") RateProvider rateProvider)
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
    public RateChange getRecentRateDynamics(String quoteCurrencyId, String baseCurrencyId)
            throws InvalidParametersException
    {
        var currentRate = rateProvider.getCurrentCurrencyRate(quoteCurrencyId, baseCurrencyId);
        var yesterdayRate = rateProvider.getHistoricalCurrencyRate(quoteCurrencyId, baseCurrencyId,
                LocalDate.now().minusDays(1));

        // Cast comparison result (integer) to enum
        return RateChange.values()[currentRate.compareTo(yesterdayRate) + 1];
    }

    @Getter
    public enum RateChange
    {
        DECREASED(-1),
        UNCHANGED(0),
        INCREASED(1);

        private int numVal;

        RateChange(int numVal)
        {
            this.numVal = numVal;
        }
    }
}
