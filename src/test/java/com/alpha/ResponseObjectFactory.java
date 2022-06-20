package com.alpha;

import com.alpha.currencyExchange.rateProviders.openExchangeRates.payload.OpenExchangeResponsePayload;
import com.alpha.visualMedia.gif.giphy.payload.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

@Component
public class ResponseObjectFactory
{
    private static Environment environment;

    @Autowired
    public ResponseObjectFactory(Environment environment)
    {
        this.environment = environment;
    }

    public static GiphyPayload initializeGooseGifResponseEntity()
    {
        var giphyObjects = new HashSet<GiphyObject>();

        var gooseOriginalImageRendition = new GiphyOriginalImageRendition(480, 261,
                "https://giphy.com/embed/lQRwl2XKnHJWE");
        var gooseImageRenditions = new GiphyImageRenditions(gooseOriginalImageRendition);
        var gooseGiphyObject = new GiphyObject("lQRwl2XKnHJWE", "Goose image", gooseImageRenditions);
        giphyObjects.add(gooseGiphyObject);

        var giphyMetaObject = new GiphyMetaObject("", 200);

        return new GiphyPayload(giphyObjects, null, giphyMetaObject);
    }

    public static OpenExchangeResponsePayload initializeRateResponseEntity()
    {
        var currencyRateStub = new HashMap<String, String>()
        {{
            put("AED", "3.673077");
            put("AFN", "89.35894");
            put("ALL", "115.187657");
            put("AMD", "422.247497");
            put("ANG", "1.803043");
            put("AOA", "434.01415");
            put("ARS", "122.913823");
            put("AUD", "1.418557");
            put("AWG", "1.8");
            put("AZN", "1.7");
            put("BAM", "1.880651");
            put("BBD", "2");
            put("BDT", "92.994243");
            put("BGN", "1.85239");
            put("BHD", "0.376965");
            put("BIF", "2058.234612");
            put("BMD", "1");
            put("BND", "1.390861");
            put("BOB", "6.878326");
            put("BRL", "5.054037");
            put("BSD", "1");
            put("BTC", "0.000048316448");
            put("BTN", "78.064124");
        }};

        return new OpenExchangeResponsePayload("disclaimer", "license", 11111111L,
                environment.getProperty("OpenExchangeRates.DefaultBaseCurrencyId"), currencyRateStub);
    }
}
