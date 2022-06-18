package com.alpha.currencyExchange.rateProviders.exceptions;

import com.alpha.common.exceptions.InvalidParametersException;

public class InvalidQuoteCurrencyException extends InvalidParametersException
{
    public InvalidQuoteCurrencyException(String message, String serviceName, String serviceURL)
    {
        super(message, serviceName, serviceURL);
    }
}
