package com.pryjda.RestApi.exceptions;

public class WrongLectureIdException extends RuntimeException {
    public WrongLectureIdException(String message) {
        super(message);
    }
}
