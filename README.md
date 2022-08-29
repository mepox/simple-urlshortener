# A simple URL Shortener

This is a small hobby project to create an URL Shortener service using Spring Boot. It uses an in-memory database (H2) to store the data.

![Alt text](screenshot.jpg?raw=true "URL Shortener")

## Installation 
The project is created with Maven, so you just need to import it to your IDE and build the project to resolve the dependencies.

## To view your H2 in-memory database
To view and query the database you can browse to http://localhost:8080/console

Login details:
```
spring.datasource.url=jdbc:h2:mem:db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=admin
spring.datasource.password=admin
```