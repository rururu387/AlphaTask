package com.alpha.exchangeRate.rateProviders.openExchangeRate;

import com.alpha.common.exceptions.InvalidParametersException;
import com.alpha.exchangeRate.rateProviders.openExchangeRate.payload.OpenExchangeResponsePayload;
import com.alpha.exchangeRate.rateProviders.exceptions.InvalidQuoteCurrencyException;
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
@Component
public class OpenExchangeRateProvider implements RateProvider
{
    @Value("${OpenExchangeRates.AppId}")
    private String applicationId;

    private OpenExchangeRatesClient openExchangeRatesClient = null;

    @Autowired
    public OpenExchangeRateProvider(OpenExchangeRatesClient openExchangeRatesClient)
    {
        this.openExchangeRatesClient = openExchangeRatesClient;
    }

    @Override
    public BigDecimal getCurrentCurrencyRate(String quoteCurrencyId, String baseCurrencyId)
            throws InvalidParametersException
    {
        OpenExchangeResponsePayload payload = null;

        payload = openExchangeRatesClient
                .getCurrentCurrencyRate(applicationId/*, baseCurrencyId*/);

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
                .getHistoricalCurrencyRate(applicationId, /*baseCurrencyId,*/ date.format(dashDateFormatter));
        var requiredRateStr = payload.getRates().get(quoteCurrencyId);

        if (requiredRateStr == null)
        {
            throw new InvalidQuoteCurrencyException(quoteCurrencyId, "Open exchange rates", "${OpenExchangeRates.URL}");
        }

        return new BigDecimal(requiredRateStr);
    }
}