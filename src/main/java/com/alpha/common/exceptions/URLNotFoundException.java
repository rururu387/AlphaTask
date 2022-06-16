package com.alpha.common.exceptions;

/**
 * Thrown if server attempts to access non-existent resource/route on OpenExchangeRates.
 */
public class URLNotFoundException extends HttpRequestException
{
    public URLNotFoundException(String message, String serviceName, String serviceURL)
    {
        super(message, serviceName, serviceURL);
    }
}
