package com.pryjda.RestApi.exceptions;

import com.pryjda.RestApi.model.response.ErrorMessage;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {Exception.class,
            WrongLectureIdException.class,
            WrongLectureIdException.class,
            EmptyUsersListException.class})
    public ResponseEntity<?> handleExceptions(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTime(LocalDateTime.now());
        errorMessage.setMessage(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTime(LocalDateTime.now());
        errorMessage.setMessage(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTime(LocalDateTime.now());
        errorMessage.setMessage(
                ex.getBindingResult().getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .findFirst()
                        .orElse(ex.getMessage())
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTime(LocalDateTime.now());

        Throwable cause = ex.getCause();
        if (cause instanceof java.time.format.DateTimeParseException) {
            errorMessage.setMessage("Bad format of time - proper pattern: 'yyyy-MM-dd HH:mm' ");
        } else if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
            errorMessage.setMessage("Bad field value of JSON - value couldn't validate");
        } else {
            errorMessage.setMessage(ex.getLocalizedMessage());
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
