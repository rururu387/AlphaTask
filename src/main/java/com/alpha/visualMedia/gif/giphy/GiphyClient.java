package com.alpha.visualMedia.gif.giphy;

import com.alpha.visualMedia.gif.giphy.payload.GiphyPayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign interface that makes queries to Giphy
 * <a href = "https://developers.giphy.com/docs/api#quick-start-guide">API</a>.
 */
@FeignClient(value = "giphy", url = "${Giphy.URL}", configuration = GiphyClientConfig.class)
public interface GiphyClient
{
    @GetMapping(value = "v1/gifs/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    GiphyPayload getGifByDescription(@RequestParam("api_key") String applicationId,
                                     @RequestParam("q") String description,
                                     @RequestParam("offset") Integer mediaOffset,
                                     @RequestParam("limit") String mediaAmount,
                                     @RequestParam("rating") String safetyRating,
                                     @RequestParam("bundle") String rendition);

    default GiphyPayload getGifByDescription(String applicationId,
                                             String description,
                                             Integer mediaOffset)
    {
        return this.getGifByDescription(applicationId, description, mediaOffset, "1", "g", "HD");
    }
}