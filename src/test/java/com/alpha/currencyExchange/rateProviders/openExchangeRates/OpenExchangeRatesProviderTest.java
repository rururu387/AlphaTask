package com.alpha.currencyExchange.rateProviders.openExchangeRates;

import com.alpha.common.exceptions.InvalidParametersException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.alpha.ResponseObjectFactory.initializeRateResponseEntity;

@SpringBootTest
@ExtendWith({MockitoExtension.class})
public class OpenExchangeRatesProviderTest
{
    @Mock
    private OpenExchangeRatesClient openExchangeRatesClientStub;

    @InjectMocks
    private OpenExchangeRatesProvider ratesProvider;

    private static Environment environment;

    @Autowired
    public OpenExchangeRatesProviderTest(OpenExchangeRatesProvider ratesProvider, Environment environment)
    {
        this.ratesProvider = ratesProvider;
        OpenExchangeRatesProviderTest.environment = environment;
    }

    @Test
    public void getCurrentCurrencyRateTest() throws InvalidParametersException
    {
        Mockito.doReturn(initializeRateResponseEntity())
                .when(openExchangeRatesClientStub)
                .getLatestCurrencyRate(environment.getProperty("OpenExchangeRates.AppId"));

        // AMD
        var actualAMDPrice = ratesProvider
                .getLatestCurrencyRate("AMD",
                        environment.getProperty("OpenExchangeRates.DefaultBaseCurrencyId"));
        var expectedAMDPrice = new BigDecimal("422.247497");

        // BBD
        var actualBBDPrice = ratesProvider
                .getLatestCurrencyRate("BBD", "${OpenExchangeRates.DefaultBaseCurrencyId}");
        var expectedBDDPrice = new BigDecimal("2");

        // BTC
        var actualBTCPrice = ratesProvider
                .getLatestCurrencyRate("BTC", "${OpenExchangeRates.DefaultBaseCurrencyId}");
        var expectedBTCPrice = new BigDecimal("0.000048316448");

        Assertions.assertEquals(expectedAMDPrice, actualAMDPrice);
        Assertions.assertEquals(expectedBDDPrice, actualBBDPrice);
        Assertions.assertEquals(expectedBTCPrice, actualBTCPrice);
    }

    @Test
    public void getHistoricalCurrencyRateTest() throws InvalidParametersException
    {
        Mockito.when(openExchangeRatesClientStub.getHistoricalCurrencyRate("2010-04-11",
                        environment.getProperty("OpenExchangeRates.AppId")))
                .thenReturn(initializeRateResponseEntity());

        // AMD
        var actualAMDPrice = ratesProvider
                .getHistoricalCurrencyRate("AMD",
                        environment.getProperty("OpenExchangeRates.DefaultBaseCurrencyId"),
                        LocalDate.of(2010, 4, 11));
        var expectedAMDPrice = new BigDecimal("422.247497");

        // BBD
        var actualBBDPrice = ratesProvider
                .getHistoricalCurrencyRate("BBD",
                        environment.getProperty("OpenExchangeRates.DefaultBaseCurrencyId"),
                        LocalDate.of(2010, 4, 11));
        var expectedBDDPrice = new BigDecimal("2");

        // BTC
        var actualBTCPrice = ratesProvider
                .getHistoricalCurrencyRate("BTC",
                        environment.getProperty("OpenExchangeRates.DefaultBaseCurrencyId"),
                        LocalDate.of(2010, 4, 11));
        var expectedBTCPrice = new BigDecimal("0.000048316448");

        Assertions.assertEquals(expectedAMDPrice, actualAMDPrice);
        Assertions.assertEquals(expectedBDDPrice, actualBBDPrice);
        Assertions.assertEquals(expectedBTCPrice, actualBTCPrice);
    }
}
