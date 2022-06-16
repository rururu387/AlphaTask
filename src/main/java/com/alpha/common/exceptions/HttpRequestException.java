package com.alpha.common.exceptions;

/**
 * Base exception to all of application's exceptions. It is used to explicitly
 */
public abstract class HttpRequestException extends Exception
{
    String serviceName;
    String serviceURL;
    public HttpRequestException(String message, String serviceName, String serviceURL)
    {
        super(message);
    }
}
