# A simple URL Shortener

This is a small hobby project to create an URL Shortener service using Spring Boot. It uses an in-memory database (H2) to store the data.

![Alt text](screenshot.jpg?raw=true "URL Shortener")

## Installation 
The project is created with Maven, so you just need to import it to your IDE and build the project to resolve the dependencies.

## Live Preview
The app is hosted on Heroku: https://spring-urlshortener.herokuapp.com/

## To view your H2 in-memory database
To view and query the database you can browse to */console*, eg.: http://localhost:8080/console

Login details:
```
spring.datasource.url=jdbc:h2:mem:db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=admin
spring.datasource.password=admin
```

# How it works

## Controllers
They are responsible to handle the client REST requests.

### HomeController.java
Responsible to handle the main page's REST requests.

- GET request to ```/urls/all``` to retrieve the list of the urls in JSON String.
- POST request to ```/urls/add``` to add a new url. The request body contains the new url as a String.
- DELETE request to ```/urls/delete/{key}``` to delete an url by it's key.

### ShortURLResolveController.java
Responsible to resolve a ShortURL using a key.

- GET request to ```/u/{key}``` resolves the ShortURL and send back a ```HttpStatus.MOVED_PERMANENTLY``` which redirects the browser to the original URL.

## Services
They are responsible to provide a service to the application.

### ShortURLService.java
Provides a service to manipulate the ShortURLs.

- .addURL(String) adds a new url to the repository after validation.
- .deleteURL(String) deletes an url from the repository.
- .getURL(String) retrieves an url from the repository.
- .getAll() retrieves a List of all urls from the repository.

### KeyGeneratorService.java (interface)
Interface for the KeyGeneratorService(s).

- .getKey() returns a key

### SimpleKeyGeneratorService.java
A simple key generator that generates a key for an url. Right now it's only returns a number starting from 1000.

## Repositories
They are responsible for database operations.

### ShortURLRepository.java (interface)
Interface for the Repositories.

- .add(ShortURL) adds a ShortURL
- .delete(int) delete a ShortURL
- .getAll() retrieves a ShortURL
- .getShortURL(int) retrieves a List of all ShortURLs
- .isExists(int) checks if a ShortURL exists

### JdbcShortURLRepository.java
Repository that uses an in-memory (H2) database with JdbcTemplate to store the data.

### ArrayListShortURLRepository.java
Repository that uses an ArrayList to store the data. Used for prototyping only.

## Models
Represent an object in the application.

### ShortURL.java
Represent a ShortURL.

- int key - the key of the ShortURL
- String url - the url of the ShortURL
