package com.accenture.payment.processor.exception;

public class NotAuthenticatedException extends RuntimeException {
    private final String fieldName;

    public NotAuthenticatedException(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
