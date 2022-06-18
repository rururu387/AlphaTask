package com.alpha.meme;

import com.alpha.ResponseObjectFactory;
import com.alpha.currencyExchange.rateProviders.exceptions.InvalidHttpRequestCurrencyException;
import com.alpha.currencyExchange.rateProviders.openExchangeRates.OpenExchangeRatesClient;
import com.alpha.visualMedia.gif.giphy.GiphyClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc
@SpringBootTest
public class MemeControllerTest
{
    MockMvc mockMvc;

    @MockBean
    GiphyClient giphyClientStub;

    @MockBean
    OpenExchangeRatesClient openExchangeRatesClientStub;

    Environment environment;

    @Autowired
    public MemeControllerTest(MockMvc mockMvc, Environment environment)
    {
        this.mockMvc = mockMvc;
        this.environment = environment;
    }

    @Test
    public void getContemporaryMemeTest() throws Exception
    {
        Mockito.doReturn(ResponseObjectFactory.initializeRateResponseEntity())
                .when(openExchangeRatesClientStub)
                .getLatestCurrencyRate(environment.getProperty("OpenExchangeRates.AppId"));

        var dashFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var yesterdayDate = LocalDate.now().minusDays(1);
        var yesterdayStr = yesterdayDate.format(dashFormatter);

        Mockito.doReturn(ResponseObjectFactory.initializeRateResponseEntity())
                .when(openExchangeRatesClientStub)
                .getHistoricalCurrencyRate(yesterdayStr, environment.getProperty("OpenExchangeRates.AppId"));

        Mockito.doReturn(ResponseObjectFactory.initializeGooseGifResponseEntity()).when(giphyClientStub)
                .getGifByDescription(Mockito.eq(environment.getProperty("Giphy.AppId")), Mockito.eq("patience"),
                        Mockito.anyInt());

        this.mockMvc.perform(get("/usd-rate/random-contemporary-memes")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("quoteCurrencyId", "BBD")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Matchers.is("lQRwl2XKnHJWE")))
                .andExpect(jsonPath("$.[0].URL", Matchers.is("https://giphy.com/embed/lQRwl2XKnHJWE")))
                .andExpect(jsonPath("$.[0].hSizePixels", Matchers.is(480)))
                .andExpect(jsonPath("$.[0].vSizePixels", Matchers.is(261)))
                .andExpect(jsonPath("$.[0].title", Matchers.is("Goose image")));
    }
}
