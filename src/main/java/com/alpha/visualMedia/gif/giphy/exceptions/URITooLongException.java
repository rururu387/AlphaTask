package com.alpha.visualMedia.gif.giphy.exceptions;

import com.alpha.common.exceptions.InvalidParametersException;

public class URITooLongException extends InvalidParametersException
{
    public URITooLongException(String message, String serviceName, String serviceURL)
    {
        super(message, serviceName, serviceURL);
    }
}
