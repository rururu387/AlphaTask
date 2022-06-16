package com.alpha.common.exceptions;

/**
 * Thrown if this server doesn't have permission to access OpenExchangeRate API's feature
 */
public class NoPermissionException extends HttpRequestException
{
    public NoPermissionException(String message, String serviceName, String serviceURL)
    {
        super(message, serviceName, serviceURL);
    }
}
