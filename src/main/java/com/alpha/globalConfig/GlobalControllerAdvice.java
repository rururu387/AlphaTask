package com.alpha.globalConfig;

import com.alpha.GeneralExceptionResponse;
import com.alpha.common.exceptions.*;
import com.alpha.currencyExchange.rateProviders.exceptions.*;
import com.alpha.visualMedia.gif.giphy.exceptions.URITooLongException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * This controller advice handles exceptions that unambiguously define handle algorithm by their nature (class
 * membership).
 */
@ControllerAdvice
public class GlobalControllerAdvice
{
    @ExceptionHandler({Throwable.class})
    public ResponseEntity<?> defaultExceptionHandler(HttpRequestException e, HandlerMethod handlerMethod,
                                                     HttpServletRequest request)
    {
        GeneralExceptionResponse response = new GeneralExceptionResponse(e.getMessage(), request,
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(response);
                //new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidAppIdException.class, NoPermissionException.class, URLNotFoundException.class})
    public ResponseEntity<?> handleInternalOpenExchangeRateExceptions(HttpRequestException e,
                                                                      HandlerMethod handlerMethod,
                                                                      HttpServletRequest request)
    {
        return defaultExceptionHandler(e, handlerMethod, request);
    }

    @ExceptionHandler(AccessRestrictedException.class)
    public ResponseEntity<?> handleAccessRestrictedExceptions(HttpRequestException e, HandlerMethod handlerMethod,
                                                              HttpServletRequest request)
    {
        GeneralExceptionResponse response = new GeneralExceptionResponse(e.getMessage(), request,
                HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ExceptionHandler(UnreadableResponseException.class)
    public ResponseEntity<?> handleUnreadableResponseExceptions(HttpRequestException e, HandlerMethod handlerMethod,
                                                                HttpServletRequest request)
    {
        GeneralExceptionResponse response = new GeneralExceptionResponse(e.getMessage(), request,
                HttpStatus.BAD_GATEWAY.value());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @ExceptionHandler({InvalidHttpRequestCurrencyException.class, InvalidQuoteCurrencyException.class,
            InvalidParametersException.class, URITooLongException.class})
    public ResponseEntity<?> handleOpenExchangeRatesExceptions(InvalidParametersException e,
                                                               HandlerMethod handlerMethod, HttpServletRequest request)
    {
        GeneralExceptionResponse response = new GeneralExceptionResponse(e.getMessage(), request,
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
