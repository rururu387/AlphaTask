package com.alpha.globalConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


@TestConfiguration
public class WireMockServerConfig
{
    Environment environment;

    @Autowired
    public WireMockServerConfig(Environment environment)
    {
        this.environment = environment;
    }

    @Bean(name = "openExchangeRatesServerMock", initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockOpenExchangeRatesService() {
        return new WireMockServer(Integer.parseInt(environment.getProperty("OpenExchangeRates.Port")));
    }

    @Bean(name = "giphyServerMock", initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockGiphyService() {
        return new WireMockServer(Integer.parseInt(environment.getProperty("Giphy.Port")));
    }
}
