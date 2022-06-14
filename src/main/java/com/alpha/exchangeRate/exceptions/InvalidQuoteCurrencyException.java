package com.alpha.exchangeRate.exceptions;

public class InvalidQuoteCurrencyException extends RateProviderException
{
    public InvalidQuoteCurrencyException(String message)
    {
        super(message);
    }
}
