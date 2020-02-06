package com.scalefocus.springtraining.moviecatalog.util;

/**
 * This ErrorMessage enum class
 * contains different error messages
 * which will be sent across the app.
 *
 * @author Kristiyan SLavov
 */
public enum ErrorMessage {
    BAD_TOKEN("Bad Token"),
    USER_DISABLED("User Disabled"),
    INVALID_CREDENTIALS("Invalid Credentials"),
    USER_NOT_FOUND("User is not found"),
    MOVIE_NOT_FOUND("Movie is not found"),
    DUPLICATE_RECORDS("Duplicate records");

    private String errorMsg;

    ErrorMessage(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return errorMsg;
    }
}
