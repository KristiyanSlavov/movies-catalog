package com.scalefocus.springtraining.moviecatalog.model.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * @author Kristiyan SLavov
 *
 * The custom error response class.
 * It represent the actual error response
 * that will be sent to the user when some exception is catched.
 */
public class ErrorResponse {

    private final String message;

    private final int httpStatus;

    private final String httpError;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;

    public ErrorResponse(String message, int httpStatus, String httpError, LocalDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.httpError = httpError;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getHttpError() {
        return httpError;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
