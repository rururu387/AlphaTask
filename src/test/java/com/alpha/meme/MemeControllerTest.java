package com.alpha.meme;

import com.alpha.ResponseObjectFactory;
import com.alpha.globalConfig.GlobalErrorMatcher;
import com.alpha.globalConfig.WireMockServerConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class contains integration tests that interact with WireMock server. Test examples are implemented below.
 * These tests may fail if:
 * <ul>
 *     <li>Exception is raised while parsing json</li>
 *     <li>Incorrect exception is raised in error decoder</li>
 *     <li>Controller advice creates an incorrect response to client of this service</li>
 * </ul>
 */
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = {WireMockServerConfig.class})
public class MemeControllerTest
{
    ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private Environment environment;
    private WireMockServer wireMockOpenExchangeServer;
    private WireMockServer wireMockGiphyServer;

    @Autowired
    public MemeControllerTest(MockMvc mockMvc,
                              @Qualifier("openExchangeRatesServerMock") WireMockServer wireMockOpenExchangeServer,
                              @Qualifier("giphyServerMock") WireMockServer wireMockGiphyServer,
                              Environment environment, ObjectMapper objectMapper)
    {
        this.mockMvc = mockMvc;
        this.environment = environment;
        this.wireMockOpenExchangeServer = wireMockOpenExchangeServer;
        this.wireMockGiphyServer = wireMockGiphyServer;
        this.objectMapper = objectMapper;
    }

    @Test
    public void getContemporaryMemeTest() throws Exception
    {
        wireMockOpenExchangeServer.stubFor(WireMock.get(WireMock.urlEqualTo("/api/latest.json?app_id="
                        + environment.getProperty("OpenExchangeRates.AppId")))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(ResponseObjectFactory
                                .initializeRateResponseEntity()))));

        var dashFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var yesterdayDate = LocalDate.now().minusDays(1);
        var yesterdayStr = yesterdayDate.format(dashFormatter);

        wireMockOpenExchangeServer.stubFor(WireMock.get(WireMock.urlEqualTo("/api/historical/" + yesterdayStr
                        + ".json?app_id=" + environment.getProperty("OpenExchangeRates.AppId")))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(ResponseObjectFactory
                                .initializeRateResponseEntity()))));

        wireMockGiphyServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/v1/gifs/search"))
                .withQueryParam("api_key", equalTo(environment.getProperty("Giphy.AppId")))
                .withQueryParam("q", equalTo("patience"))
                .withQueryParam("offset", matching("\\d+"))
                .withQueryParam("limit", equalTo("1"))
                .withQueryParam("rating", equalTo("g"))
                .withQueryParam("bundle", equalTo("HD"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(ResponseObjectFactory
                                .initializeGooseGifResponseEntity()))));

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

    /**
     * This is an example of test that performs request to a mock server.

     */
    @Test
    public void getContemporaryMemeOpenExchangeInvalidBaseCurrencyTest() throws Exception
    {
        wireMockOpenExchangeServer.stubFor(WireMock.get(WireMock
                        .urlEqualTo("/api/latest.json?app_id="
                                + environment.getProperty("OpenExchangeRates.AppId")))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\n" +
                                "  \"error\": true,\n" +
                                "  \"status\": 400,\n" +
                                "  \"message\": \"invalid_app_id\",\n" +
                                "  \"description\": \"Your advertising could be here\"\n" +
                                "}")));

        var resultActions = this.mockMvc.perform(get("/api/v1/usd-rate/random-contemporary-memes")
                .accept(MediaType.APPLICATION_JSON)
                .param("quoteCurrencyId", "BBD")
        );

        GlobalErrorMatcher.isErrorResponseFormattedCorrectly(resultActions, HttpStatus.BAD_REQUEST,
                "User provided an invalid base currency. Visit " +
                        "https://docs.openexchangerates.org/docs/supported-currencies for more information.",
                "http://localhost/api/v1/usd-rate/random-contemporary-memes",
                java.util.Map.of("quoteCurrencyId", new String[]{"BDD"}));

    }

    @Test
    public void getContemporaryMemeOpenExchangeInvalidQuoteCurrencyTest() throws Exception
    {
        wireMockOpenExchangeServer.stubFor(WireMock.get(WireMock
                        .urlEqualTo("/api/latest.json?app_id="
                                + environment.getProperty("OpenExchangeRates.AppId")))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(ResponseObjectFactory.initializeRateResponseEntity()))
                ));

        var resultActions = this.mockMvc.perform(get("/api/v1/usd-rate/random-contemporary-memes")
                .accept(MediaType.APPLICATION_JSON)
                .param("quoteCurrencyId", "Geese are cool!")
        );

        GlobalErrorMatcher.isErrorResponseFormattedCorrectly(resultActions, HttpStatus.BAD_REQUEST,
                "User provided an invalid quote currency. Visit " +
                        "https://docs.openexchangerates.org/docs/supported-currencies for more information.",
                "http://localhost/api/v1/usd-rate/random-contemporary-memes",
                java.util.Map.of("quoteCurrencyId", new String[]{"Geese are cool!"}));
    }
}
