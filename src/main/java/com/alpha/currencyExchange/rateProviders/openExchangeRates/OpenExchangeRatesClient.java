package com.alpha.currencyExchange.rateProviders.openExchangeRates;

import com.alpha.currencyExchange.rateProviders.openExchangeRates.payload.OpenExchangeResponsePayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Feign interface that makes queries to Open exchange rates <a href = "https://docs.openexchangerates.org">API</a>.
 */
@FeignClient(value = "openExchangeRates", url = "${OpenExchangeRates.URL}",
        configuration = OpenExchangeRatesClientConfig.class)
public interface OpenExchangeRatesClient
{
    @GetMapping(value = "/api/latest.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    OpenExchangeResponsePayload getLatestCurrencyRate(@RequestParam("app_id") String applicationId/*,
                                                          @RequestParam("base") String baseCurrencyId*/);

    @GetMapping(value = "/api/historical/{date}.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    OpenExchangeResponsePayload getHistoricalCurrencyRate(@PathVariable("date") String dateStr,
                                                          @RequestParam("app_id") String applicationId/*,
                                                          @RequestParam("base") String baseCurrencyId*/);
}
