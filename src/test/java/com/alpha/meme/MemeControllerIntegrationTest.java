package com.alpha.meme;

import com.alpha.ResponseObjectFactory;
import com.alpha.currencyExchange.rateProviders.openExchangeRates.OpenExchangeRatesClient;
import com.alpha.currencyExchange.rateProviders.openExchangeRates.OpenExchangeRatesErrorDecoder;
import com.alpha.globalConfig.WireMockServerConfig;
import com.alpha.visualMedia.gif.giphy.GiphyClient;
import com.alpha.visualMedia.gif.giphy.GiphyErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Using @MockBean to implement integration tests. Mocking whole feign client interface. Thus, error decoder and json
 * serializer are not invoked and tested.
 * Testes only for Http.OK responses.
 */
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = {WireMockServerConfig.class})
public class MemeControllerIntegrationTest
{
    private MockMvc mockMvc;
    @MockBean
    private GiphyClient giphyClientMock;
    @MockBean
    private OpenExchangeRatesClient openExchangeRatesClientMock;
    private Environment environment;
    private OpenExchangeRatesErrorDecoder openExchangeRatesErrorDecoder;
    private GiphyErrorDecoder giphyErrorDecoder;
    private WireMockServer wireMockOpenExchangeServer;
    private WireMockServer wireMockGiphyServer;
    ObjectMapper objectMapper;

    @Autowired
    public MemeControllerIntegrationTest(MockMvc mockMvc,
                              @Qualifier("openExchangeRatesServerMock") WireMockServer wireMockOpenExchangeServer,
                              @Qualifier("giphyServerMock") WireMockServer wireMockGiphyServer,
                              Environment environment, ObjectMapper objectMapper)
    {
        this.mockMvc = mockMvc;
        this.environment = environment;
        this.openExchangeRatesErrorDecoder = new OpenExchangeRatesErrorDecoder();
        this.giphyErrorDecoder = new GiphyErrorDecoder();
        this.wireMockOpenExchangeServer = wireMockOpenExchangeServer;
        this.wireMockGiphyServer = wireMockGiphyServer;
        this.objectMapper = objectMapper;
    }

    @Test
    public void getContemporaryMemeTest() throws Exception
    {
        Mockito.doReturn(ResponseObjectFactory.initializeRateResponseEntity())
                .when(openExchangeRatesClientMock)
                .getLatestCurrencyRate(environment.getProperty("OpenExchangeRates.AppId"));

        var dashFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var yesterdayDate = LocalDate.now().minusDays(1);
        var yesterdayStr = yesterdayDate.format(dashFormatter);


        Mockito.doReturn(ResponseObjectFactory.initializeRateResponseEntity())
                .when(openExchangeRatesClientMock)
                .getHistoricalCurrencyRate(yesterdayStr, environment.getProperty("OpenExchangeRates.AppId"));

        Mockito.doReturn(ResponseObjectFactory.initializeGooseGifResponseEntity()).when(giphyClientMock)
                .getGifByDescription(Mockito.eq(environment.getProperty("Giphy.AppId")), Mockito.eq("patience"),
                        Mockito.anyInt(), 1);

        this.mockMvc.perform(get("/api/v1/usd-rate/random-contemporary-memes")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("quoteCurrencyId", "BBD")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.visualMediaObjects.[0].id", Matchers.is("lQRwl2XKnHJWE")))
                .andExpect(jsonPath("$.visualMediaObjects.[0].URL", Matchers.is("https://giphy.com/embed/lQRwl2XKnHJWE")))
                .andExpect(jsonPath("$.visualMediaObjects.[0].hSizePixels", Matchers.is(261)))
                .andExpect(jsonPath("$.visualMediaObjects.[0].wSizePixels", Matchers.is(480)))
                .andExpect(jsonPath("$.visualMediaObjects.[0].title", Matchers.is("Goose image")));
    }
}
