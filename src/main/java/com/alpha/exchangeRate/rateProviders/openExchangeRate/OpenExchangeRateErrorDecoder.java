package com.alpha.exchangeRate.rateProviders.openExchangeRate;

import com.alpha.common.exceptions.*;
import com.alpha.exchangeRate.rateProviders.exceptions.*;
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

/**
 *  This class contains method that decodes all <a href = "https://docs.openexchangerates.org/docs/errors">errors </a>
 *  that may occur during requests to OpenExchangeRates API. Status codes and messages are transferred to exceptions
 *  here.
 */
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
                log.error("Could not close reader of response body");
            }
        }
        return message;
    }

    @Override
    public Exception decode(String methodKey, Response response)
    {
        if (response == null)
        {
            return new IOException("Recieved no response from OpenExchangeRates (${OpenExchangeRates.URL})");
        }

        final String serviceName = "Open exchange rate";
        final String serviceURL = "${OpenExchangeRates.URL}";
        switch (response.status())
        {
            case 400 ->
            {
                return new InvalidHttpRequestCurrencyException("User provided an invalid base currency. Visit " +
                        "https://docs.openexchangerates.org/docs/supported-currencies for more information.",
                        serviceName, serviceURL);
            }

            case 404 ->
            {
                return new URLNotFoundException("Could not find resource by requested path and query parameters.",
                        serviceName, serviceURL);
            }

            case 429 ->
            {
                return new NoPermissionException("Server has no permission to access requested feature.",
                        serviceName, serviceURL);
            }
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
                    return new InvalidAppIdException("No application ID provided.", serviceName, serviceURL);
                }
                else
                {
                    return new InvalidAppIdException("Service did not accept provided application ID.", serviceName,
                            serviceURL);
                }
            }

            case 403 ->
            {
                if (message.getDescription() == null || message.getDescription().isBlank())
                {
                    return new AccessRestrictedException("Exceeded request limit.", serviceName, serviceURL);
                }
                return new AccessRestrictedException(message.getDescription(), serviceName, serviceURL);
            }
        }

        return new UnreadableResponseException("Response contains error. Reason is unknown.", serviceName, serviceURL);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static class ExceptionMessage
    {
        private String timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private String description;
    }
}
