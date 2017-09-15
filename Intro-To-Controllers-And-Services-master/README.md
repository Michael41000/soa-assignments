# Intro To Controllers And Services

#### Description
In this assignment you will practice newly acquired knowledge of Spring to create a basic Controller and a supporting Service from the ground up.

#### Duration
2-3 hours

#### Skills
Spring fundamentals such as Inversion of Control, Dependency Injection, Spring Dependencies, Components, Controllers, & Services. REST and HTTP fundamentals such as Endpoints, URL Conventions, Path Variables, Path Parameters, HTTP Methods, HTTP Payload, JSON, HTTP Headers, HTTP Status Codes.

## Concept
The concept of this assignment is to create a Spring Boot API project that will keep track of People and their Friends.

## Instructions

* Use the [Spring Initializr](http://start.spring.io/) to create a Spring Boot project named *"Friendlr"*
* You will need to include at minimum the dependencies
    * Web
	* DevTools
* Create a POJO named *"Person"* with at least the fields
    * ID - `Long`
	* First Name - `String`
	* Last Name - `String`
* Create a *PersonService* and inject it into the *PersonController*. 
* Create a Controller named *"PersonController"* that controls the *"/person"* url
* Create the following endpoints in the *PersonController*, and implement their behavior in the *PersonService*
  1. `GET /person`
    * This will retrieve a list of all PersonDtos
  2. `GET /person/{id}`
    * This will retrieve a single PersonDto as indicated by the {id}, or return the 404 - Not Found status code if that ID does not exist
  3. `POST /person`
    * This will accept JSON in the form of a PersonDto, create a Person instance, assign an ID to that Person, and store the Person instance in a collection in the *PersonService*. It will then return a 201 - Created status code. Return the 404 - Not Found status code if that ID does not exist
  4. `PUT /person/{id}`
    * This will overwrite the Person with the indicated {id} with the unmarshalled JSON contents of the body of this request or return the 404 - Not Found status code if that ID does not exist
  5. `DELETE /person/{id}`
    * This will delete the Person with the ID specified and remove all references to the specified ID in the Friends list of all other Person objects. Return the 404 - Not Found status code if that ID does not exist