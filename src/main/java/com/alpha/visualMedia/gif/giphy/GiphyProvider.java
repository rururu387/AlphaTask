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
                                    image.getImages().getOriginalRendition().getMp4MediaURL(),
                                    image.getImages().getOriginalRendition().getWidth(),
                                    image.getImages().getOriginalRendition().getHeight(),
                                    image.getTitle());

            visualMediaObjectSet.add(visualMediaObject);
        }

        return visualMediaObjectSet;
    }
}
