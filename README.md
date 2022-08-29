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

# How it works

## Controllers
They are responsible to handle the client REST calls.

### HomeController.java
Responsible to handle the main page's REST calls.

- GET request to ```/urls/all``` to retrieve the list of the urls in JSON String.
- POST request to ```/urls/add``` to add a new url. The request body contains the new url as a String.
- DELETE request to ```/urls/delete/{key}``` to delete an url by it's key.

### ShortURLResolveController.java
Responsible to resolve a ShortURL using a key.

- GET request to ```/u/{key}``` resolves the ShortURL and send back a ```HttpStatus.MOVED_PERMANENTLY``` which redirects the browser to the original URL.

## Services
They are responsible to provide a service to our application.

### ShortURLService.java
Provides a service to manipulate the ShortURLs.

- .addURL(String) adds a new url to the repository after validation.
- .deleteURL(String) deletes an url from the repository.
- .getURL(String) retrieves an url from the repository.
- .getAll() retrieves a List of all urls from the repository.

### SimpleKeyGeneratorService.java
A simple key generator that generates a key for an url.

- .getKey() returns a key
