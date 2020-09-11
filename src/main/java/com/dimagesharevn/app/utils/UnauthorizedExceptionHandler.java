package com.dimagesharevn.app.utils;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedExceptionHandler extends AuthenticationException {
    public UnauthorizedExceptionHandler(String msg) {
        super(msg);
    }
}
