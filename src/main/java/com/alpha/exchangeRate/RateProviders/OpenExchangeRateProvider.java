package com.alpha.exchangeRate.RateProviders;

import com.alpha.exchangeRate.RateProvider;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

/**
 * This class implements fetching and parsing data from website: https://openexchangerates.org
 */
@Getter
@Component
public class OpenExchangeRateProvider implements RateProvider
{
    public static final String applicationId = "57676e267330400596732bd46c5e85e6";

    OpenExchangeRates openExchangeRates = Feign.builder().decoder(new JacksonDecoder())
            .target(OpenExchangeRates.class, "https://api.github.com");

    @Override
    public BigDecimal getCurrentCurrencyRate(String fromCurrencyId, String toCurrencyId)
    {
        var payload = openExchangeRates
                .getCurrentCurrencyRate(applicationId, toCurrencyId);

        var requiredRateStr = payload.getRates().get(fromCurrencyId);
        return new BigDecimal(requiredRateStr);
    }

    @Override
    public BigDecimal getHistoricalCurrencyRate(String fromCurrencyId, String toCurrencyId, LocalDate date)
    {
        var dashDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var payload = openExchangeRates
                .getHistoricalCurrencyRate(date.format(dashDateFormatter), applicationId, toCurrencyId);

        var requiredRateStr = payload.getRates().get(fromCurrencyId);
        return new BigDecimal(requiredRateStr);
    }
}
