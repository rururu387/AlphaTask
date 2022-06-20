package com.alpha.visualMedia.gif.giphy;

import com.alpha.visualMedia.VisualMediaObject;
import com.alpha.visualMedia.VisualMediaProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class GiphyProvider implements VisualMediaProvider
{
    @Value("${Giphy.AppId}")
    private String applicationId;

    private GiphyClient giphyClient = null;

    @Autowired
    public GiphyProvider(GiphyClient giphyClient)
    {
        this.giphyClient = giphyClient;
    }

    /**
     * Performs request to Giphy server.
     * @param description query string that gets passed to Giphy as a query parameter to search for.
     * @return set of gif's metatada objects. For application to be easier to extend return value is a set, not a
     * single object, even though task was to display a single gif.
     */
    @Override
    public Set<VisualMediaObject> getVisualMediaByDescription(String description)
    {
        Random randomGenerator = new Random();
        var randomMediaNumber = randomGenerator.ints(0, 25).findFirst().getAsInt();
        var giphyObject = giphyClient.getGifByDescription(applicationId, description, randomMediaNumber);

        var visualMediaObjectSet = new HashSet<VisualMediaObject>();

        for (var image : giphyObject.getGifObject())
        {
            var visualMediaObject = new VisualMediaObject(image.getId(),
                                    image.getImages().getOriginalRendition().getGifMediaURL(),
                                    image.getImages().getOriginalRendition().getHeight(),
                                    image.getImages().getOriginalRendition().getWidth(),
                                    image.getTitle());

            visualMediaObjectSet.add(visualMediaObject);
        }

        return visualMediaObjectSet;
    }
}
