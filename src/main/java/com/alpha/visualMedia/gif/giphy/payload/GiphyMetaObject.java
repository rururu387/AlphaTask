package com.alpha.visualMedia.gif.giphy.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class stores part of giphy.com response to query API. Contains query status and error message
 * @See <a href = "https://developers.giphy.com/docs/api/schema#meta-object">documentation</a>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiphyMetaObject
{
    @JsonProperty("msg")
    private String message;
    private Integer status;
}
