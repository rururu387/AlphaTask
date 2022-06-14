package com.alpha.exchangeRate.RateProviders.OpenExchangeRate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "openExchangeRates", url = "https://openexchangerates.org",
        configuration = OpenExchangeRatesClientConfig.class)
public interface OpenExchangeRatesClient
{
    @GetMapping(value = "/api/latest.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    OpenExchangeRatesPayload getCurrentCurrencyRate(@RequestParam("app_id") String applicationId,
                                                    @RequestParam("base") String baseCurrency);

    @GetMapping(value = "/api/historical/{date}.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    OpenExchangeRatesPayload getHistoricalCurrencyRate(@RequestParam("app_id") String applicationId,
                                                       @RequestParam("base") String baseCurrency,
                                                       @PathVariable("date") String dateStr);

    @Getter
    @Setter
    @NoArgsConstructor
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
