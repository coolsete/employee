package com.io.framey.exception;

public class DeserializerException extends RuntimeException {
    public DeserializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeserializerException(String message) {
        super(message);
    }
}
