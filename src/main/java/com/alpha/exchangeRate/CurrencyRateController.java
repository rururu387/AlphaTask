package com.alpha.exchangeRate;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Integer getExchangeRateToDollar(@RequestParam("currencyId") String fromCurrencyId)
    {
        return currencyRateService.getRecentRateChange(fromCurrencyId, "USD");
    }
}
