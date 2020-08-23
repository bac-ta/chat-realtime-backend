package com.dimagesharevn.app.utils;

import com.dimagesharevn.app.constants.APIMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceptionHandler extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ExceptionHandler(String resourceName, String fieldName, Object fieldValue) {
        super(String.format(APIMessage.RESOURCE_NOT_FOUND, resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionHandler(String message) {
        super(message);
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
