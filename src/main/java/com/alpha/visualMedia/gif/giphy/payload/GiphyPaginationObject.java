package com.alpha.visualMedia.gif.giphy.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class stores part of giphy.com response to query API
 * @See <a href = "https://developers.giphy.com/docs/api/schema#pagination-object">documentation</a>
 */
@Getter
@Setter
@NoArgsConstructor
public class GiphyPaginationObject
{
    @JsonProperty("count")
    private Integer imagesAmount;
}
