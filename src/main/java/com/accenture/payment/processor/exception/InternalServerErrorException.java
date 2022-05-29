package com.accenture.payment.processor.exception;

public class InternalServerErrorException extends RuntimeException {
    private final String fieldName;

    public InternalServerErrorException(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

}
