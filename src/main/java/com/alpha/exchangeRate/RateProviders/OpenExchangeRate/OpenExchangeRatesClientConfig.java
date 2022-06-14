package com.alpha.exchangeRate.RateProviders.OpenExchangeRate;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;


public class OpenExchangeRatesClientConfig
{
    @Bean
    public ErrorDecoder errorDecoder()
    {
        return new OpenExchangeRateErrorDecoder();
    }
}
