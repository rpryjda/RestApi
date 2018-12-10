package com.pryjda.RestApi.exceptions;

import com.pryjda.RestApi.model.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {StudentServiceException.class, Exception.class})
    public ResponseEntity<?> handleStudentServiceException(StudentServiceException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTime(LocalDateTime.now());
        errorMessage.setMessage(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
