package com.alpha.exchangeRate.rateProviders.openExchangeRate;

import com.alpha.exchangeRate.rateProviders.openExchangeRate.payload.OpenExchangeResponsePayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Feign interface that makes queries to Open exchange rates <a href = "https://docs.openexchangerates.org">API</a>.
 * @Note Since changing the API `base` currency is not available for free plan this request parameter is commented
 * everywhere.
 */
@FeignClient(value = "openExchangeRates", url = "${OpenExchangeRates.URL}",
        configuration = OpenExchangeRatesClientConfig.class)
public interface OpenExchangeRatesClient
{
    @GetMapping(value = "/api/latest.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    OpenExchangeResponsePayload getCurrentCurrencyRate(@RequestParam("app_id") String applicationId/*,
                                                       @RequestParam("base") String baseCurrency*/);

    @GetMapping(value = "/api/historical/{date}.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    OpenExchangeResponsePayload getHistoricalCurrencyRate(@RequestParam("app_id") String applicationId,
                                                          /*@RequestParam("base") String baseCurrency,*/
                                                          @PathVariable("date") String dateStr);
}
