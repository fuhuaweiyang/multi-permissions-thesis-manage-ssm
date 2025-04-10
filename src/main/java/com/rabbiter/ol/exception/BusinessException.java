package com.rabbiter.ol.exception;

// BusinessException.java
public class BusinessException extends RuntimeException {
    private int code = 400;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    // getters...
}