package com.alpha.common.exceptions;

import lombok.AllArgsConstructor;

/**
 * Access restricted for repeated over-use, or other reason (message should contain description).
 */
public class AccessRestrictedException extends HttpRequestException
{
    public AccessRestrictedException(String message, String serviceName, String serviceURL)
    {
        super(message, serviceName, serviceURL);
    }
}
