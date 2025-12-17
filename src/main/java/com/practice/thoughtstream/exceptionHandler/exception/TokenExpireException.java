package com.practice.thoughtstream.exceptionHandler.exception;

public class TokenExpireException extends Exception {
    public TokenExpireException(String tokenExpire) {
        super(tokenExpire);
    }
}
