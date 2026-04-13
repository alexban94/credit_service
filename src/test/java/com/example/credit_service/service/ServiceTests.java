package com.example.credit_service.service;

import com.example.credit_service.services.application.service.ApplicationService;
import com.example.credit_service.common.dto.CreditRequest;
import com.example.credit_service.common.dto.CreditResponse;
import com.example.credit_service.common.exception.ApplicationNotFoundException;
import com.example.credit_service.services.application.model.CustomerApplication;
import com.example.credit_service.services.application.repository.ApplicationRepo;
import com.example.credit_service.event.EventRepo;
import com.example.credit_service.services.risk.service.RiskCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

// Use Mockito in this test class to enable @Mock/@InjectMocks.
@ExtendWith(MockitoExtension.class)
public class ServiceTests {
    // Create Mock dependencies. Behaviour and return values of these do nothing unless defined.
    // These are essentially test doubles to isolate service behaviour for testing.
    @Mock
    private ApplicationRepo appRepo;

    @Mock
    private EventRepo eventRepo;

    @Mock
    private RiskCalculator calc;

    // Create a real service object and inject the mocks into it.
    @InjectMocks
    private ApplicationService service;

    @Test
    void shouldApproveApplication(){

        // Create an input - the request DTO, a submitted application.
        CreditRequest request = new CreditRequest("Pasta", "Bake",
                "FFXIV", 50000, 10000);

        // Define mock behaviour.
        when(calc.calculateRiskScore(50000, 10000)).thenReturn(80); //return this when method is called with these inputs.
        when(calc.decision(80)).thenReturn("APPROVED");

        // accept any input and return it dynamically with thenAnswer.
        // object passed into thenAnswer() is InvocationOnMock object. use it to get first argument and return it like the save method does.
        when(appRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        // Call the method being tested.
        CreditResponse response = service.submitApplication(request);

        // Check if method output response has the correct values.
        // Assertions. first param is message shown if it fails.
        assertEquals("Decision should be APPROVED","APPROVED", response.decision());
        assertEquals("Risk Score should be 80", response.riskScore(), 80);
        assertNotNull("DTO appID should be set", response.appID());

        // Verify - check methods were called on the mocks. Useful if bugs are introduced e.g. method call removed when modifying class.
        verify(calc).calculateRiskScore(50000, 10000);
        verify(calc).decision(80);
        verify(appRepo, times(1)).save(any());
        verify(eventRepo, times(1)).save(any());

   }

    @Test
    void shouldRejectApplication(){
        CreditRequest request = new CreditRequest("Pasta", "Bake",
                "FFXIV", 10000, 50000);

        // Define mock behaviour.
        when(calc.calculateRiskScore(10000, 50000)).thenReturn(10); //return this when method is called with these inputs.
        when(calc.decision(10)).thenReturn("REJECTED");

        // accept any input and return it dynamically with thenAnswer.
        // object passed into thenAnswer() is InvocationOnMock object. use it to get first argument and return it like the save method does.
        when(appRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        // Call the method being tested.
        CreditResponse response = service.submitApplication(request);

        // Check if method output response has the correct values.
        // Assertions. first param is message shown if it fails.
        assertEquals("Decision should be REJECTED","REJECTED", response.decision());
        assertEquals("Risk Score should be 10", response.riskScore(), 80);
        assertNotNull("DTO appID should be set", response.appID());

        // Verify - check methods were called on the mocks. Useful if bugs are introduced e.g. method call removed when modifying class.
        verify(calc).calculateRiskScore(10000, 50000);
        verify(calc).decision(10);
        // Check they have saved only once.
        verify(appRepo, times(1)).save(any());
        verify(eventRepo, times(1)).save(any());

    }

    @Test
    void shouldGetApplication(){
        //Test for a valid application that exists in the database.
        CustomerApplication app = new CustomerApplication("TestID", "Pasta", "Bake", "FFXIV", 10000, 50000, 80, "APPROVED", false, false);

        when(appRepo.findById("TestID")).thenReturn(Optional.of(app));

        CreditResponse response = service.getApplication("TestID");

        assertEquals(null,"TestID", response.appID());
        assertEquals(null, "APPROVED", response.decision());
        assertEquals(null, 80, response.riskScore());

        // Check repo was queried by ID in the method we are testing.
        verify(appRepo).findById("TestID");

        // If application exists it should
    }

    @Test
    void shouldGetApplicationError(){
        //findByID method implemented by SpringData returns Optional container object instead of null.
        // safer to use and indicates it may or may not return a CustomerApplication.
        when(appRepo.findById("TestID")).thenReturn(Optional.empty());

        //assertThrows requires a lambda function as an Executable type, so it controls when it runs and when the exception is thrown.
        // compared against passing in service.getApplication() directly, the method will run immediately and
        // exception is thrown before assertThrows even sees it. with this, it can call executable.execute() function to run it.
        // assertThrows asserts that it throws a specific exception matching the one provided.
        // save it here in a variable so we can also test the error message.
        ApplicationNotFoundException e = assertThrows(ApplicationNotFoundException.class,
                () -> service.getApplication("TestID")
        );

        assertEquals("Exception error message does not match", "Application not found: TestID", e.getMessage());

        verify(appRepo, times(1)).findById("TestID");

    }


}
