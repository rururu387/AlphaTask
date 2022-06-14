package com.alpha.exchangeRate.exceptions;

public class UnreadableResponseException extends RateProviderException
{
    public UnreadableResponseException(String message)
    {
        super(message);
    }
}
