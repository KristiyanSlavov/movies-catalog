package com.scalefocus.springtraining.moviecatalog.exception;

/**
 * The movie duplicate key exception class.
 * It is a custom exception that will be sent to the user
 * if he tries to insert the same movie twice.
 *
 * @author Kristiyan SLavov
 */
public class MovieDuplicateKeyException extends Exception {

    public MovieDuplicateKeyException(String message) {
        super(message);
    }
}
