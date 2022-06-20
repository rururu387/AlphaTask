package com.alpha.visualMedia.gif.giphy;

import com.alpha.common.exceptions.*;
import com.alpha.visualMedia.gif.giphy.exceptions.URITooLongException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
/**
 * This class contains method that decodes all <a href = "https://developers.giphy.com/docs/api#response-codes">errors
 * </a> that may occur during requests to Giphy API. Status codes are transferred to exceptions here.
 */
@Slf4j
public class GiphyErrorDecoder implements ErrorDecoder
{
    @Override
    public Exception decode(String methodKey, Response response)
    {
        if (response == null)
        {
            return new IOException("Recieved no response from Giphy (${Giphy.URL})");
        }

        final String serviceName = "Giphy";
        final String serviceURL = "${Giphy.URL}";

        switch (response.status())
        {
            case 400 ->
            {
                return new InvalidParametersException("Request was formatted incorrectly or missing a required " +
                        "parameter(s).", serviceName, serviceURL);
            }

            case 403 ->
            {
                return new NoPermissionException("Server is not authorised to make a request.",
                        serviceName, serviceURL);
            }

            case 404 ->
            {
                return new URLNotFoundException("Requested source not found. Try changing request route or parameters.",
                        serviceName, serviceURL);
            }

            case 414 ->
            {
                return new URITooLongException("Search sentence is too long. It must be 50 or less symbols length.",
                        serviceName, serviceURL);
            }

            case 429 ->
            {
                return new AccessRestrictedException("Too many requests to this service from this server.",
                        serviceName, serviceURL);
            }
        }

        return new UnreadableResponseException("Response contains error. Reason is unknown.", serviceName, serviceURL);
    }
}
