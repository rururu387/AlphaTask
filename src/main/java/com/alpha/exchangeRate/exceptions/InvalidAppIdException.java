package com.alpha.exchangeRate.exceptions;

/**
 * Thrown if application id is not accepted by server (id is missing or incorrect). Internal server error.
 */
public class InvalidAppIdException extends RateProviderException
{
    public InvalidAppIdException(String message)
    {
        super(message);
    }
}
