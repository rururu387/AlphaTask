package com.alpha.exchangeRate.RateProviders.OpenExchangeRate;

import com.alpha.exchangeRate.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class OpenExchangeRateErrorDecoder implements ErrorDecoder
{
    public ExceptionMessage readExceptionFromBody(Response response)
    {
        ExceptionMessage message = null;
        Reader reader = null;

        try
        {
            reader = response.body().asReader(StandardCharsets.UTF_8);
            char[] arr = new char[8 * 1024];
            StringBuilder buffer = new StringBuilder();
            int numCharsRead;
            while ((numCharsRead = reader.read(arr, 0, arr.length)) != -1)
            {
                buffer.append(arr, 0, numCharsRead);
            }
            String responseBody = buffer.toString();


            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            message = mapper.readValue(responseBody,
                    ExceptionMessage.class);
        }
        catch (JsonProcessingException e)
        {
            log.warn("Could not parse body of error (json)");
        }
        catch (IOException e)
        {
            log.warn("Could not read body of response containing error");
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                log.error("OpenExchangeRateErrorDecoder class: could not close reader of response body");
            }
        }
        return message;
    }

    @Override
    public Exception decode(String methodKey, Response response)
    {
        String baseMessage = "Request forwarded to OpenExchangeRate. ";
        if (response.status() == 400)
        {
            return new InvalidBaseCurrencyException(baseMessage + "User provided an invalid base currency. Visit " +
                    "https://docs.openexchangerates.org/docs/supported-currencies for more information.");
        }

        ExceptionMessage message = readExceptionFromBody(response);

        if (message == null)
        {
            return new IOException();
        }

        switch (response.status())
        {
            case 401 ->
            {
                if (message.getMessage().equals("missing_app_id"))
                {
                    return new InvalidAppIdException(baseMessage + "No application ID provided.");
                }
                else
                {
                    return new InvalidAppIdException(baseMessage + "Service did not accept provided application ID.");
                }
            }

            case 403 ->
            {
                if (message.getDescription() == null || message.getDescription().isBlank())
                {
                    return new AccessRestrictedException(baseMessage + "Exceeded request limit.");
                }
                return new AccessRestrictedException(message.getDescription());
            }

            case 404 ->
            {
                return new URLNotFoundException(baseMessage + "Resource not found: " + message.getPath() + ".");
            }

            case 429 ->
            {
                return new NoPermissionException(baseMessage + "Server cannot access requested feature:"
                        + message.getPath() + ".");
            }
        }

        return new UnreadableResponseException(baseMessage + "Response contains error. Reason is unknown.");
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ExceptionMessage
    {
        private String timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private String description;
    }
}
