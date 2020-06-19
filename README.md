# Movie Catalog Project: Spring Boot Application that makes use of JWT authentcation for securing an exposed REST API.

## Overview

 - [Requirements](#requiremnts)
 - [Installation](#installation)
 - [Usage](#usage)
 - [Authentication](#authentication)
 - [API Documentation]($api-documentation)
   - [Movie](#movie)
 
## Requirements

Running this project requires

 - Java JDK language level 8
 - Maven
 - MongoDB
 - Postman (for testing)

## Installation

Clone this project. Before building the project, first you have to download and install MongoDB Compass and then you have to change the database credentials in application.properties file, according to your configuration.

Download MongoDB Compass from here: https://docs.mongodb.com/compass/master/install/

Download Postman from here: https://www.postman.com/downloads/

## Usage

According to the user role, every user has different rights to perform certain actions. 

Admin users (with 'ADMIN' role) can get, add, delete or modify movies, while the users with role 'USER' can only get information about movies and nothing more.


## Authentication

This application makes use of JWT authentication for securing an exposed REST API.
It uses hard coded user values for User Authentication. User will be able to consume this API only
if it has a valid JSON Web Token (JWT).

The Application expose REST POST API with mapping/authenticate using
which User will get a valid JSON Web Token.

On passing correct username and password it will generate a JSON Web Token(JWT). If user tries to access API it will allow access only if request has a valid JSON Web Token(JWT)

The hardcoded users are:
- username: javainuse | password: password | role: ADMIN
- username: testuser | password: 12345678 | role: USER


Authentication: Create a POST request with url localhost:8080/authenticate. Body should have valid username and password. In our case username is javainuse/testuser and password is password/12345678.
```
{
	"username":"javainuse",
	"password":"password"
}
```
```
{
	"username":"testuser",
	"password":"12345678"
}
```
Then it will generate a response that contains a token type, JSON Web Token and expiration date, like this example:
```
{
    "tokenType": "bearer",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTU5MjU2ODgwMCwiaWF0IjoxNTkyNTY1MjAwfQ.--qGba63K7I5ifVH7IV2QmWycMMGXfAyoultYRv5FEIGPl4zXWUXbXNEvspqBB4H4wia2twD_zcZedPPf9wwuQ",
    "expiresIn": "2020-06-19T15:13:20"
}
```
## API Documentation

### Movie

For every API access the already generated JWT must be provided in the headers.

Required data for Movie creation or update:

```json
{
    "title": "Animals 2",
    "writer": "Ivan Ivanov",
    "genre": "Animation",
    "runtime": "123 min",
    "releaseDate": "2010-10-10",
    "rate": "9.5"
}
```

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/authenticate` | POST | *Login user and get JWT* |
| `/movies` | GET | *Retrieves all movies* |
| `/movies/:id` | GET | *Retrieves a movie by it's id* |
| `/movies/movie` | POST | *Create a movie* |
| `/movies/:id` | PUT | *Update a movie* |
| `/movies/:id` | DELETE | *Delete a movie* |

