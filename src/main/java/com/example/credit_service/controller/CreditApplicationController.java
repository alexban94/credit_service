package com.example.credit_service.controller;

import com.example.credit_service.dto.CreditRequest;
import com.example.credit_service.dto.CreditResponse;
import com.example.credit_service.service.CreditApplicationService;
import org.springframework.web.bind.annotation.*;

/* RestController is essentially a combination of @Controller (handles web requests) and @ResponseBody (return values
 should be written to the HTTP response body). Marks class as REST Controller and serializes return values to JSON.
 Also makes it a Spring managed @Bean */
@RestController //This is an API not a website, so it  returns JSON data instead of a HTML page (view).
@RequestMapping("/applications") //Base path for all endpoints in this controller, groups related endpoints.
public class CreditApplicationController {

    private final CreditApplicationService service;

    public CreditApplicationController(CreditApplicationService service){
        //Dependency Injection. Spring finds a CreditApplicationService bean and calls this instructor, injecting the dependency.
        this.service = service;
    }

    /* Both Get/Post endpoints represent the same resource (credit application), returning the current state of it.
    * Post creates it, get fetches it and returns it. */

    // @PostMapping registers this method as a handler and POST requests get routed here, gets invoked when the path matches.
    @PostMapping
    public CreditResponse submit(@RequestBody CreditRequest request){
        // @RequestBody means it contains JSON and to convert it into a Java object. Spring reads HTTP request body and
        // uses a JSON converter (Jackson). Deserializes JSON into the object type specified (DTO) to pass into the method.
        // If JSON is invalid then it's a 400 error, bad request.
        return service.submitApplication(request);
    }

    // @GetMapping Handles get requests and path variables let you identify a specific resource.
    // @PathVariable binds part of the url to a method parameter. In this case extracts {id} and converts it to string.
    // If missing or invalid returns a 400 error. Name /{..} must match the @PathVariable variable name.
    @GetMapping("/{id}")
    public CreditResponse getByID(@PathVariable String id){
        return service.getApplication(id);
    }
}
