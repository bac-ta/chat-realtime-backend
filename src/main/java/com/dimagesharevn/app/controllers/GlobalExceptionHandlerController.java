package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.rests.response.APIErrorResponse;
import com.dimagesharevn.app.models.rests.response.LoginResponse;
import com.dimagesharevn.app.utils.HttpClientExceptionHandler;
import com.dimagesharevn.app.utils.ResourceNotFoundExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticateException(AuthenticationException ex) {
        return ResponseEntity.badRequest().body(new LoginResponse(APIMessage.LOGIN_FAILURE, null));
    }
    /**
     * Provides handling for exceptions throughout this service.
     *
     * @param ex      The target exception
     * @param request The current request
     */
    @ExceptionHandler({
            ResourceNotFoundExceptionHandler.class, HttpClientExceptionHandler.class
    })
    @Nullable
    public final ResponseEntity<APIErrorResponse> handleNotFoundException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        LOGGER.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

        if (ex instanceof ResourceNotFoundExceptionHandler) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ResourceNotFoundExceptionHandler exception = (ResourceNotFoundExceptionHandler) ex;

            return handleNotFoundException(exception, headers, status, request);
        } else if (ex instanceof HttpClientExceptionHandler) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            HttpClientExceptionHandler exception = (HttpClientExceptionHandler) ex;

            return handleHttpClientException(exception, headers, status, request);
        } else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Unknown exception type: " + ex.getClass().getName());
            }

            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }


    /**
     * Customize the response for ResourceNotFoundExceptionHandler.
     *
     * @param ex      The exception
     * @param headers The headers to be written to the response
     * @param status  The selected response status
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<APIErrorResponse> handleNotFoundException(ResourceNotFoundExceptionHandler ex,
                                                                       HttpHeaders headers, HttpStatus status,
                                                                       WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new APIErrorResponse(errors), headers, status, request);
    }

    /**
     * Customize the response for HttpClientExceptionHandler.
     *
     * @param ex      The exception
     * @param headers The headers to be written to the response
     * @param status  The selected response status
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<APIErrorResponse> handleHttpClientException(HttpClientExceptionHandler ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new APIErrorResponse(errors), headers, status, request);
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
     * @param headers The headers for the response
     * @param status  The response status
     * @param request The current request
     */
    protected ResponseEntity<APIErrorResponse> handleExceptionInternal(Exception ex, @Nullable APIErrorResponse body,
                                                                       HttpHeaders headers, HttpStatus status,
                                                                       WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
