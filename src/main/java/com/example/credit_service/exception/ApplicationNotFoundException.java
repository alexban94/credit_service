package com.example.credit_service.exception;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException(String id) {
        // Call super class so error propagates.
        // TODO: review super class RuntimeException functionality.
        super("Application not found: " + id);
    }
}
