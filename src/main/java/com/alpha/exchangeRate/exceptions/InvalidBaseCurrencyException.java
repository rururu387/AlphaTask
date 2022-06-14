package com.alpha.exchangeRate.exceptions;

/**
 * Thrown if user specified an invalid currency id for a base currency. List of supported currencies
 * <a href = "https://docs.openexchangerates.org/docs/supported-currencies">here</a>.
 */
public class InvalidBaseCurrencyException extends RateProviderException
{
    public InvalidBaseCurrencyException(String message)
    {
        super(message);
    }
}
