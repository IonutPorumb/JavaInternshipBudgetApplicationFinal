package com.accenture.payment.processor.exception;

public class ForbiddenException extends RuntimeException {
    private final String fieldName;

    public ForbiddenException(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

}
