package com.example.credit_service.dto;

public record CreditRequest (
        String firstName,
        String lastName,
        String employer,
        int annualIncome,
        int requestAmount
){}
