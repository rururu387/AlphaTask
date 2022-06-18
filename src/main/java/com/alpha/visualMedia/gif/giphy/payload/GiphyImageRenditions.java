package com.alpha.visualMedia.gif.giphy.payload;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Stores multiple image retentions
 * @See <a href = "https://developers.giphy.com/docs/api/schema/#image-object">API documentation</a>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiphyImageRenditions
{
    @JsonProperty("original")
    GiphyOriginalImageRendition originalRendition;
    @JsonProperty("hd")
    @JsonAlias({"HD", "mp4"})
    Object hd;
}
