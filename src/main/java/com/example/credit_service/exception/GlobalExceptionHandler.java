package com.example.credit_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Centralized error handling with @ControllerAdvice. Tells Spring that this class should apply to all controllers
// in the application. Without it would need try/catch inside every controller method.
@ControllerAdvice
class GlobalExceptionHandler {
    //TODO: test this.
    // @ExceptionHandler with the class file makes Spring call this method any time this class of exception is thrown.
    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<String> handleApplicationNotFound(ApplicationNotFoundException e){
        // ResponseEntity is a Spring class that represents an entire HTTP response - can control status code/headers/body.
        // ResponseEntity<T> where T is the body.
        // HttpStatus status and error code, HttpHeaders headers.
        // status is a static factory method that returns a builder object (refer to builder design pattern). .body creates and returns the actual ResponseEntity object.
        // HttpStatus enum class containing status code/messages.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // e.getMessage() retrieves the application ID and error message from the custom exception class.
    }

    // THis kind of error gets thrown when @Valid on @ResponseBody fails.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException e){
        //TODO: add logic to precisely format response to show which fields failed validation.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
