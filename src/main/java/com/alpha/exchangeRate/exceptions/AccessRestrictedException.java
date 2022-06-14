package com.alpha.exchangeRate.exceptions;

/**
 * Access restricted for repeated over-use, or other reason (message should contain description).
 */
public class AccessRestrictedException extends RateProviderException
{
    public AccessRestrictedException(String message)
    {
        super(message);
    }
}
