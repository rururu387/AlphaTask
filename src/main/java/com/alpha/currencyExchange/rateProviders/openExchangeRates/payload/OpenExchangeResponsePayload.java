package com.alpha.currencyExchange.rateProviders.openExchangeRates.payload;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public
class OpenExchangeResponsePayload
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
