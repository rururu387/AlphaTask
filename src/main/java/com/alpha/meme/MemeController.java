package com.alpha.meme;

import com.alpha.GeneralExceptionResponse;
import com.alpha.common.exceptions.InvalidParametersException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * This is a controller that allows users get memes about rate changes.
 */
@Controller
public class MemeController
{
    MemeService memeService;

    @Autowired
    public MemeController(MemeService memeService)
    {
        this.memeService = memeService;
    }

    @GetMapping(value = "/usd-rate/random-contemporary-memes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getContemporaryMeme(@RequestParam("quoteCurrencyId") String quoteCurrencyId)
            throws InvalidParametersException, JsonProcessingException
    {
        String responseBody = memeService.getRecentRateMemes(quoteCurrencyId,
                "${OpenExchangeRates.DefaultBaseCurrencyId}");

        return ResponseEntity.ok(responseBody);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<?> handleInternalJacksonExceptions(JacksonException e, HandlerMethod handlerMethod,
                                                             HttpServletRequest request) {
        GeneralExceptionResponse response = new GeneralExceptionResponse("Could not serialize objects to JSON",
                request, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
