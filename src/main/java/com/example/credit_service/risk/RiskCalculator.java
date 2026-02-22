package com.example.credit_service.risk;

import org.springframework.stereotype.Component;

// Managed by Spring. @Component is the class-level annotation.
@Component
public class RiskCalculator {
    // basic class with simple methods to calculate risk.
    // return score as 0 if requested amount is 0, else return the output of the calculation.
    public int calculateRiskScore(int income, int amount){
        return amount == 0 ? 0 : (amount * 100)/ income;
    }

    //approve if score is below 50. need to test where risk values normally fall.
    public String decision(int score){
        return score <= 50 ? "APPROVED" : "DENIED" ;
    }
}
