package com.alpha.exchangeRate.rateProviders.exceptions;

import com.alpha.common.exceptions.InvalidParametersException;

/**
 * Thrown if user specified an invalid currency id for a base currency. List of supported currencies
 * <a href = "https://docs.openexchangerates.org/docs/supported-currencies">here</a>.
 */
public class InvalidHttpRequestCurrencyException extends InvalidParametersException
{
    public InvalidHttpRequestCurrencyException(String message, String serviceName, String serviceURL)
    {
        super(message, serviceName, serviceURL);
    }
}
