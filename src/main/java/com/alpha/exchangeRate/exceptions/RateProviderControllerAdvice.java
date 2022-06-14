package com.alpha.exchangeRate.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RateProviderControllerAdvice
{
    @ExceptionHandler({ AccessRestrictedException.class, InvalidAppIdException.class, NoPermissionException.class,
            URLNotFoundException.class })
    public ResponseEntity<?> handleInternalExceptions(RateProviderException e, HandlerMethod handlerMethod,
                                             HttpServletRequest request) {
        RateProviderExceptionResponse response = new RateProviderExceptionResponse(e.getMessage(), request,
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ InvalidBaseCurrencyException.class, InvalidQuoteCurrencyException.class })
    public ResponseEntity<?> handleUserExceptions(RateProviderException e, HandlerMethod handlerMethod,
                                             HttpServletRequest request) {
        RateProviderExceptionResponse response = new RateProviderExceptionResponse(e.getMessage(), request,
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RateProviderException.class)
    public ResponseEntity<?> handleUnreadableResponseExceptions(RateProviderException e, HandlerMethod handlerMethod,
                                                  HttpServletRequest request) {
        RateProviderExceptionResponse response = new RateProviderExceptionResponse(e.getMessage(), request,
                HttpStatus.SERVICE_UNAVAILABLE.value());
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Getter
    public static class RateProviderExceptionResponse
    {
        private String message;
        private Long timestamp;
        private String servicePath;
        private Integer status;

        public RateProviderExceptionResponse(String message, HttpServletRequest request, Integer status)
        {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
            this.servicePath = request.getRequestURL().toString();
            this.status = status;
        }
    }
}
