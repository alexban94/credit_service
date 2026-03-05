package com.example.credit_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreditRequest (
        // Added validation annotations to fields for requests.
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String employer,

        @Min(0)
        int annualIncome,

        @Min(1)
        int requestAmount
){}
