# Simple URL Shortener

A small hobby project to create an URL Shortener service using Spring Boot.

![Alt text](screenshot.jpg?raw=true "URL Shortener")

## What's inside
The project uses the following technologies:
- Java 11
- Spring Boot
- HTML, CSS and JavaScript
- SQL
- Maven

## Overview
The service uses Controller - Service - Repository pattern:

- Controllers: Handle the clients' REST API requests and pass them to the Services.
- Services: Provide a service to the application. Receive input from Controllers, perform validation and business logic, and calling Repositories for data manipulation.
- Repositories: Responsible to database operations.

The main components of the service are:

- TinyURLService: Provide a service to manipulate the TinyURLs.
- SimpleKeyGeneratorServce: A placeholder that generate a key and return an Integer starting from 1000.
- RandomKeyGeneratorService: Generate a 4 letter key from characters [a-z, A-Z, 0-9].

## Installation 
The project is created with Maven, so you just need to import it to your IDE and build the project to resolve the dependencies.

## Live Preview
The app is hosted on Heroku: https://spring-urlshortener.herokuapp.com/

## Data storage
It uses an in-memory database (H2) to store the data.

To view and query the database you can browse to */console*, eg.: http://localhost:8080/console

Login details:
```
spring.datasource.url=jdbc:h2:mem:db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=admin
spring.datasource.password=admin
```

## Endpoints

HomeController:

- GET request to ```/urls/all``` to retrieve the list of the urls in JSON String.
- POST request to ```/urls/add``` to add a new url. The request body contains the new url as a String.
- DELETE request to ```/urls/delete/{key}``` to delete an url by it's key.

TinyURLResolveController:

- GET request to ```/u/{key}``` resolves a TinyURL and send back a ```HttpStatus.MOVED_PERMANENTLY``` which redirects the browser to the original URL.
