package com.alpha.visualMedia.gif.giphy.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Stores information that is necessary to show gif/image: url, size, width and height, ...
 * @See <a href = "https://developers.giphy.com/docs/api/schema/#image-object">API documentation, original: object</a>
 */
@Getter
@Setter
@NoArgsConstructor
public class GiphyOriginalImageRendition
{
    private Integer width;
    private Integer height;
    @JsonProperty("mp4")
    private String mp4MediaURL;
}
