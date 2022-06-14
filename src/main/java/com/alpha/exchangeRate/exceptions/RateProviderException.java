package com.alpha.exchangeRate.exceptions;

public abstract class RateProviderException extends Exception
{
    public RateProviderException(String message)
    {
        super(message);
    }
}
