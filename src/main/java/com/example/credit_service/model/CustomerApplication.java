package com.example.credit_service.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Class to represent business state to use in the service class.
// Lombok annotations to create getters, setters and constructors (and reduce written code).
@Getter
@Setter
@NoArgsConstructor //may be needed to instantiate it.
@AllArgsConstructor

// @Document is from Spring Data, and marks a class as a MongoDB document and maps it to a MongoDB collection
// called 'applications'. Spring will also treat the class as persistable.
// Reminder that MongoDB stores data as JSON-like objects called documents.
@Document(collection = "applications")
public class CustomerApplication {

    //@Id indicates this field is the primary identifier of the document. I.e. primary key in relational databases.
    // When saving to MongoDB, it will check the id field. If null it creates a new _id, if it's set then MongoDB
    // updates the existing document.
    // The benefit of this is you don't need to spend time writing any insert queries/update logic or serialization code.
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String employer;
    private int requestAmount;
    private int annualIncome;
    private int riskScore;
    private String decision;


}
