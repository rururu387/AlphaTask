package com.alpha.com.visualMedia.gif.giphy;

import com.alpha.visualMedia.gif.giphy.GiphyClient;
import com.alpha.visualMedia.gif.giphy.GiphyProvider;
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

import static com.alpha.ResponseObjectFactory.initializeGooseGifResponseEntity;

@SpringBootTest
@ExtendWith({MockitoExtension.class})
public class GiphyProviderTest
{
    @Mock
    GiphyClient giphyClientStub;

    @InjectMocks
    private GiphyProvider giphyProvider;

    private Environment environment;

    @Autowired
    public GiphyProviderTest(GiphyProvider giphyProvider, Environment environment)
    {
        this.giphyProvider = giphyProvider;
        this.environment = environment;
    }

    @Test
    public void getVisualMediaByDescriptionTest()
    {
        Mockito.doReturn(initializeGooseGifResponseEntity()).when(giphyClientStub)
                .getGifByDescription(Mockito.eq(environment.getProperty("Giphy.AppId")), Mockito.eq("Goose"),
                        Mockito.anyInt());

        var visualMediaObjects = giphyProvider.getVisualMediaByDescription("Goose");

        Assertions.assertNotNull(visualMediaObjects);
        Assertions.assertEquals(1, visualMediaObjects.size());

        var visualMediaObject = visualMediaObjects.iterator().next();

        Assertions.assertEquals("lQRwl2XKnHJWE", visualMediaObject.getId());
        Assertions.assertEquals("Goose image", visualMediaObject.getTitle());
        Assertions.assertEquals("https://giphy.com/embed/lQRwl2XKnHJWE", visualMediaObject.getURL());
        Assertions.assertEquals(480, visualMediaObject.getHSizePixels());
        Assertions.assertEquals(261, visualMediaObject.getVSizePixels());
    }
}