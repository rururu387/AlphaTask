package com.alpha.visualMedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VisualMediaService
{
    VisualMediaProvider visualMediaProvider;

    @Autowired
    public VisualMediaService(@Qualifier("giphyProvider") VisualMediaProvider visualMediaProvider)
    {
        this.visualMediaProvider = visualMediaProvider;
    }

    public Set<VisualMediaObject> getMediaObjectsByDescription(String description)
    {
        return visualMediaProvider.getVisualMediaByDescription(description);
    }
}
