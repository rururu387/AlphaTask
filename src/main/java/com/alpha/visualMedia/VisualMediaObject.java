package com.alpha.visualMedia;

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
    private String url;
    private Integer hSizePixels;
    private Integer vSizePixels;
    private String title;
}
