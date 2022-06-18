package com.alpha.visualMedia.gif.giphy.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class describes single image/gif.
 * @See <a href = "https://developers.giphy.com/docs/api/schema/#gif-object">API documentation</a>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiphyObject
{
    String id;
    String title;
    GiphyImageRenditions images;
}
