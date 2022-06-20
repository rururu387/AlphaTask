package com.alpha.visualMedia;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisualMediaObject
{
    private String id;
    @JsonProperty("URL")
    private String URL;
    @JsonProperty("hSizePixels")
    private Integer hSizePixels;
    @JsonProperty("wSizePixels")
    private Integer wSizePixels;
    private String title;
}
