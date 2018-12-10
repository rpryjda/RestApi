package com.pryjda.RestApi.exceptions;

public class StudentServiceException extends RuntimeException {
    public StudentServiceException(String message) {
        super(message);
    }
}
