package com.alpha.exchangeRate.RateProviders;

import feign.Param;
import feign.RequestLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Map;

@Component
public interface OpenExchangeRates
{
    @RequestLine("GET /api/latest.json?app_id{;applicationId}&base{;baseCurrency}")
    OpenExchangeRatesPayload getCurrentCurrencyRate(@Param("applicationId") String applicationId,
                                    @Param("baseCurrency") String baseCurrency);

    @RequestLine("GET /api/historical/{date}.json?app_id{;applicationId}&base{;baseCurrency}")
    OpenExchangeRatesPayload getHistoricalCurrencyRate(@Param("applicationId") String applicationId,
                                       @Param("baseCurrency") String baseCurrency, @Param("date") String dateStr);

    @AllArgsConstructor
    class OpenExchangeRatesPayload
    {
        private String disclaimer;
        private String license;
        private Long timestamp;
        private String base;
        private Map<String, String> rates;

        public Map<String, String> getRates()
        {
            return rates;
        }
    }
}
