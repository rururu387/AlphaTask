package com.alpha.common.exceptions;

public class InvalidParametersException extends HttpRequestException
{
    public InvalidParametersException(String message, String serviceName, String serviceURL)
    {
        super(message, serviceName, serviceURL);
    }
}
