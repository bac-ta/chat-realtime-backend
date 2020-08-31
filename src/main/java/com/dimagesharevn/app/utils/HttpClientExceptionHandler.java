package com.dimagesharevn.app.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HttpClientExceptionHandler extends HttpClientErrorException {
    public HttpClientExceptionHandler(HttpStatus statusCode, String message) {
        super(statusCode, message);
    }


}
