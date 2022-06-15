package com.alpha.exchangeRate.RateProviders.OpenExchangeRate;

import com.alpha.exchangeRate.exceptions.InvalidQuoteCurrencyException;
import com.alpha.exchangeRate.exceptions.RateProviderException;
import com.alpha.exchangeRate.RateProvider;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class implements fetching and parsing data from <a href="https://openexchangerates.org">this website</a>
 */
@Getter
@Component
public class OpenExchangeRateProvider implements RateProvider
{
    @Value("${OpenExchangeRate.AppId}")
    public static final String applicationId = null;

    private OpenExchangeRatesClient openExchangeRatesClient = null;

    @Autowired
    public OpenExchangeRateProvider(OpenExchangeRatesClient openExchangeRatesClient)
    {
        this.openExchangeRatesClient = openExchangeRatesClient;
    }

    @Override
    public BigDecimal getCurrentCurrencyRate(String quoteCurrencyId, String baseCurrencyId) throws RateProviderException
    {
        OpenExchangeRatesClient.OpenExchangeRatesPayload payload = null;

        payload = openExchangeRatesClient
                .getCurrentCurrencyRate(applicationId, baseCurrencyId);

        var requiredRateStr = payload.getRates().get(quoteCurrencyId);

        if (requiredRateStr == null)
        {
            throw new InvalidQuoteCurrencyException(quoteCurrencyId);
        }

        return new BigDecimal(requiredRateStr);
    }

    @Override
    public BigDecimal getHistoricalCurrencyRate(String quoteCurrencyId, String baseCurrencyId,
                                                LocalDate date) throws RateProviderException
    {
        var dashDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        var payload = openExchangeRatesClient
                .getHistoricalCurrencyRate(applicationId, baseCurrencyId, date.format(dashDateFormatter));
        var requiredRateStr = payload.getRates().get(quoteCurrencyId);

        if (requiredRateStr == null)
        {
            throw new InvalidQuoteCurrencyException(quoteCurrencyId);
        }

        return new BigDecimal(requiredRateStr);
    }
}