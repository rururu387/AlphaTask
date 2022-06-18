package com.alpha.currencyExchange.rateProviders.openExchangeRates;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;


public class OpenExchangeRatesClientConfig
{
    @Bean
    public ErrorDecoder errorDecoder()
    {
        return new OpenExchangeRatesErrorDecoder();
    }
}
