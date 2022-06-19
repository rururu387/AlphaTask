package com.alpha.currencyExchange.rateProviders.exceptions;

import com.alpha.common.exceptions.InvalidParametersException;

/**
 * Thrown if user specified an invalid currency id for a base currency. List of supported currencies
 * <a href = "https://docs.openexchangerates.org/docs/supported-currencies">here</a>.
 */
public class InvalidBaseCurrencyException extends InvalidParametersException
{
    public InvalidBaseCurrencyException(String message, String serviceName, String serviceURL)
    {
        super(message, serviceName, serviceURL);
    }
}
