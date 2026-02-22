package com.example.credit_service.repository;

import com.example.credit_service.domain.CustomerApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

// Using an interface is the new best practice with using Spring Data. Previously you would use @Repository
// but you need more code. This way, Spring generates the implementation rather than having to write it.
// At startup it scans for repo interfaces and creates a proxy class, implementing all CRUD methods and wires it.
// Same pattern works for other Repos like SQL/JPA.
// @Repository is still needed when you need full control over queries and need to do complex joins/aggregations.
// Note that Repository the created object does not store data itself but acts as a gateway/access layer to a real database.
public interface ApplicationRepo extends MongoRepository<CustomerApplication, String> {

}