package com.pryjda.RestApi.exceptions;

public class EmptyUsersListException extends RuntimeException {
    public EmptyUsersListException(String message) {
        super(message);
    }
}
