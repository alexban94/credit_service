# Event-Driven Credit Service

## Overview
Backend project simulating a credit system that processes applications using an **event-driven architecture**.  Uses an implementation of the transactional outbox design pattern to save events to an outbox collection to ensure they are not lost. Events are received by a consumer, passed to a router which determines which specific handler to use based on the event type; the handler calls the relevant service to perform the business logic.

Currently built as a **modular monolith**, but designed so each module has loose or no coupling and can be easily extracted into microservices later.

## How it works
1. User submits a credit application via REST API  
2. Application is saved in MongoDB  
3. An event is written to an outbox collection  
4. A publisher sends the event to Kafka  
5. Other modules consume the event and process:
   - Risk scoring  
   - Fraud checks  
   - Decisioning  
6. The application is updated asynchronously as results come back  

## Structure
- `application` – handles requests + contains application database. 
- `risk` – calculates risk score  
- `fraud` – fraud checks (in progress) 
- `decision` – aggregates results + makes final decision (in progress) 
- `status` - for frontend visualisation (in progress) 
- `common` – shared DTOs + event logic  

## Tech
- Java + Spring Boot  
- Spring Data + MongoDB
- Spring Security
- Apache Kafka  
- Jackson  
- JUnit / Mockito  

## Key ideas
- Outbox pattern for reliable events  
- Consumer → Router → Handler pattern  
- Eventual consistency (async updates)  

## TODO: 
- Adding retries / DLQ / ensure idempotency.
- Frontend visualisation of event flow
- Extracting into microservices 
