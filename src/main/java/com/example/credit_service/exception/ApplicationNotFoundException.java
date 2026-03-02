package com.example.credit_service.exception;

// RuntimeException (unchecked) and does not require 'throws' in method signatures.
// If the service throws this error it will propagate up the call stack until something handles it, in this case Spring
// with @ControllerAdvice.
public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException(String id) {
        // Call super class constructor to set the erorr message stored in the exception.
        // Error message indicates the given application ID does not exist in the database
        super("Application not found: " + id);
    }
}
