package com.pryjda.RestApi.exceptions;

public class WrongUserEmailException extends RuntimeException {
    public WrongUserEmailException(String message) {
        super(message);
    }
}
