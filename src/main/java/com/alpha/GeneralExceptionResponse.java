package com.alpha;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is used to fill body of response if any exception occurred in this application. It contains basic
 * information needed for debugging.
 */
@Getter
public class GeneralExceptionResponse
{
    private final String message;
    private final Long timestamp;
    private final String servicePath;
    private final String queryString;
    private final Integer status;

    public GeneralExceptionResponse(String message, HttpServletRequest request, Integer status)
    {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.servicePath = request.getRequestURL().toString();
        this.queryString = request.getQueryString();
        this.status = status;
    }
}
