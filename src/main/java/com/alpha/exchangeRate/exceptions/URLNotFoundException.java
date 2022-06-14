package com.alpha.exchangeRate.exceptions;

/**
 * Thrown if server attempts to access non-existent resource/route on OpenExchangeRates.
 */
public class URLNotFoundException extends RateProviderException
{
    public URLNotFoundException(String message)
    {
        super(message);
    }
}
