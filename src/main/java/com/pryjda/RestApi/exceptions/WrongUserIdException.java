package com.pryjda.RestApi.exceptions;

public class WrongUserIdException extends RuntimeException {
    public WrongUserIdException(String message) {
        super(message);
    }
}
