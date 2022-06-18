package com.alpha.currencyExchange.rateProviders.openExchangeRates;

import com.alpha.common.exceptions.InvalidParametersException;
import com.alpha.currencyExchange.rateProviders.exceptions.InvalidQuoteCurrencyException;
import com.alpha.currencyExchange.RateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class implements fetching and parsing data from <a href="https://openexchangerates.org">this website</a>
 */
@Component
public class OpenExchangeRatesProvider implements RateProvider
{
    @Value("${OpenExchangeRates.AppId}")
    private String applicationId;

    private OpenExchangeRatesClient openExchangeRatesClient = null;

    @Autowired
    public OpenExchangeRatesProvider(OpenExchangeRatesClient openExchangeRatesClient)
    {
        this.openExchangeRatesClient = openExchangeRatesClient;
    }

    @Override
    public BigDecimal getLatestCurrencyRate(String quoteCurrencyId, String baseCurrencyId)
            throws InvalidParametersException
    {
        var payload = openExchangeRatesClient
                .getLatestCurrencyRate(applicationId/*, baseCurrencyId*/);

        var requiredRateStr = payload.getRates().get(quoteCurrencyId);

        if (requiredRateStr == null)
        {
            throw new InvalidQuoteCurrencyException(quoteCurrencyId, "Open exchange rates", "${OpenExchangeRates.URL}");
        }

        return new BigDecimal(requiredRateStr);
    }

    @Override
    public BigDecimal getHistoricalCurrencyRate(String quoteCurrencyId, String baseCurrencyId,
                                                LocalDate date) throws InvalidParametersException
    {
        var dashDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var payload = openExchangeRatesClient
                .getHistoricalCurrencyRate(date.format(dashDateFormatter), applicationId/*, baseCurrencyId*/);
        var requiredRateStr = payload.getRates().get(quoteCurrencyId);

        if (requiredRateStr == null)
        {
            throw new InvalidQuoteCurrencyException(quoteCurrencyId, "Open exchange rates", "${OpenExchangeRates.URL}");
        }

        return new BigDecimal(requiredRateStr);
    }
}