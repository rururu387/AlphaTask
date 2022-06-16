package com.alpha.exchangeRate.rateProviders.openExchangeRate.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
