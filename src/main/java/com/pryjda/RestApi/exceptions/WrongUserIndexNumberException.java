package com.pryjda.RestApi.exceptions;

public class WrongUserIndexNumberException extends RuntimeException {
    public WrongUserIndexNumberException(String message) {
        super(message);
    }
}
