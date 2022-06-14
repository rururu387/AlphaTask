package com.alpha.exchangeRate.exceptions;

/**
 * Thrown if this server doesn't have permission to access OpenExchangeRate API's feature
 */
public class NoPermissionException extends RateProviderException
{
    public NoPermissionException(String message)
    {
        super(message);
    }
}
