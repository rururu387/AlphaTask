package com.alpha.visualMedia.gif.giphy;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class GiphyClientConfig
{
    @Bean
    public ErrorDecoder errorDecoder()
    {
        return new GiphyErrorDecoder();
    }
}
