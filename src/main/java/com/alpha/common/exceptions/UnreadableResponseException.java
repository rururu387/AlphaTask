package com.alpha.common.exceptions;

public class UnreadableResponseException extends HttpRequestException
{
    public UnreadableResponseException(String message, String serviceName, String serviceURL)
    {
        super(message, serviceName, serviceURL);
    }
}
