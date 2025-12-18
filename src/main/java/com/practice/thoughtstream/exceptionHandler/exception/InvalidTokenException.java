package com.practice.thoughtstream.exceptionHandler.exception;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String invalidRequest) {
        super(invalidRequest);
    }
}
