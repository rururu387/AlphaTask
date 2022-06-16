package com.alpha.common.exceptions;

/**
 * Thrown if application id is not accepted by server (id is missing or incorrect). Internal server error.
 */
public class InvalidAppIdException extends HttpRequestException
{
    public InvalidAppIdException(String message, String serviceName, String serviceURL)
    {
        super(message, serviceName, serviceURL);
    }
}
