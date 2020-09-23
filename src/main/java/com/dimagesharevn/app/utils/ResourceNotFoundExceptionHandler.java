package com.dimagesharevn.app.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author bac-ta
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundExceptionHandler extends RuntimeException {
    public ResourceNotFoundExceptionHandler(String message) {
        super(message);
    }
}
