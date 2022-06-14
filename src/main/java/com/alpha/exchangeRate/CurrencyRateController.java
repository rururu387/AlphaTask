package com.alpha.exchangeRate;

import com.alpha.exchangeRate.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CurrencyRateController
{
    CurrencyRateService currencyRateService;

    @Autowired
    public CurrencyRateController(CurrencyRateService currencyRateService)
    {
        this.currencyRateService = currencyRateService;
    }

    @GetMapping("/rate-to/usd")
    public ResponseEntity<?> getRecentExchangeRateToDollarDynamics(
            @RequestParam("quoteCurrencyId") String quoteCurrencyId) throws RateProviderException
    {
        // See return value of getRecentRateChange(...)
        Integer currencyRateDynamics = null;
        return ResponseEntity.ok(
                "{\n" +
                      "\t\"recentRateChange\": " + currencyRateService.getRecentRateDynamics(quoteCurrencyId, "USD")
                      + "\n}");
    }
}
