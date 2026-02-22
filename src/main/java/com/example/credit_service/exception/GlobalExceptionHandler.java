package com.example.credit_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Centralized error handling with @ControllerAdvice.
@ControllerAdvice
class GlobalExceptionHandler {
    //TODO: test this.
    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ApplicationNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
