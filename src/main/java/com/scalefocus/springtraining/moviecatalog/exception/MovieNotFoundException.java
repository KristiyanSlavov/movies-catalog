package com.scalefocus.springtraining.moviecatalog.exception;

/**
 * The movie not found exception class.
 * It is a custom exception that will be sent to the user
 * if some requested movie is not found in the database.
 *
 * @author Kristiyan SLavov
 */
public class MovieNotFoundException extends Exception {

    public MovieNotFoundException(String message) {
        super(message);
    }
}
