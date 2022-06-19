package com.alpha;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    private final Map<String, String[]> queryParameterMap;
    private final Integer status;

    public GeneralExceptionResponse(String message, HttpServletRequest request, Integer status)
    {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.servicePath = request.getRequestURL().toString();
        this.queryParameterMap = request.getParameterMap();
        this.status = status;
    }
}
