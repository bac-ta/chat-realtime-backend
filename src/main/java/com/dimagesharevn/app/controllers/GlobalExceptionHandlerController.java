package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.rests.response.APIErrorResponse;
import com.dimagesharevn.app.models.rests.response.LoginResponse;
import com.dimagesharevn.app.utils.UnauthorizedExceptionHandler;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticateException() {
        return ResponseEntity.badRequest().body(new LoginResponse(APIMessage.LOGIN_FAILURE, null));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<APIErrorResponse> handleHttpClientException(HttpClientErrorException exception, WebRequest request) {
        LOGGER.error("Handling " + exception.getClass().getSimpleName() + " due to " + exception.getMessage());
        return handleHttpClientException(exception, exception.getStatusCode(), request);
    }

    @ExceptionHandler(UnauthorizedExceptionHandler.class)
    public ResponseEntity<APIErrorResponse> handleUnauthorizedException(UnauthorizedExceptionHandler exception) {
        LOGGER.error("Handling " + exception.getClass().getSimpleName() + " due to " + exception.getMessage());
        return new ResponseEntity<>(new APIErrorResponse(APIMessage.ENDTRY_POINT_UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Customize the response for HttpClientExceptionHandler.
     *
     * @param ex     The exception
     * @param status The selected response status
     * @return a {@code ResponseEntity} instance
     */
    private ResponseEntity<APIErrorResponse> handleHttpClientException(HttpClientErrorException ex,
                                                                       HttpStatus status,
                                                                       WebRequest request) {
        if (status == HttpStatus.CONFLICT) {
            Map object = new Gson().fromJson(ex.getResponseBodyAsString(), Map.class);
            return handleExceptionInternal(ex, new APIErrorResponse(APIMessage.REGIST_USER_CONFLICT, status.value()), status, request);
        }

        return handleExceptionInternal(ex, new APIErrorResponse(ex.getMessage(), status.value()), status, request);
    }

    /**
     * A single place to customize the response body of all Exception types.
     *
     * <p>The default implementation sets the {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE}
     * request attribute and creates a {@link ResponseEntity} from the given
     * body, headers, and status.
     *
     * @param ex      The exception
     * @param body    The body for the response
     * @param status  The response status
     * @param request The current request
     */
    private ResponseEntity<APIErrorResponse> handleExceptionInternal(Exception ex, @Nullable APIErrorResponse body,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, null, status);
    }
}
