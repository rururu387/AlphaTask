package com.alpha.visualMedia.gif.giphy.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * This class contains response to Giphy API query. It consists of metadata about animated images (e.g. URLs to consume,
 * title, available renditions), their amount and query metadata. Some response information is ignored.
 * @See <a href = "https://developers.giphy.com/docs/api/endpoint#search">API documentation</a>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiphyPayload
{
    @JsonProperty("data")
    Set<GiphyObject> gifObject;
    GiphyPaginationObject pagination;
    @JsonProperty("meta")
    GiphyMetaObject giphyMetaObject;
}
