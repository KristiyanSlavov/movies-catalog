package com.scalefocus.springtraining.moviecatalog.controller.advice;

import com.scalefocus.springtraining.moviecatalog.model.error.ErrorResponse;
import com.scalefocus.springtraining.moviecatalog.exception.MovieDuplicateKeyException;
import com.scalefocus.springtraining.moviecatalog.exception.MovieNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kristiyan SLavov
 * <p>
 * The custom global exception handler class.
 * This class is resposinble for global error handling
 * and can handles both custom exceptions and existing exceptions.
 * It gives full control over the body of the response as well as the status code,
 * mapping of several exceptions to the same method, to be handled together, and
 * makes good use of the RESTful @ResponseEntity response.
 */
@ControllerAdvice
public class MovieExceptionHandler extends ResponseEntityExceptionHandler {

    //Validate for Validating Path Variables and Request Params
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(Exception ex) throws IOException {
        //response.sendError(HttpStatus.BAD_REQUEST.value());
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFound(Exception ex) {
        ErrorResponse errors = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieDuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleMoviesDuplicateKey(Exception ex) {
        ErrorResponse errors = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //Error handler for @Valid
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("DD-mm-yyyy hh:mm:ss")));
        body.put("status", status.value());

        //Get all fields errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    //Error handler for Request Method
    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                      HttpHeaders headers,
                                                                      HttpStatus status,
                                                                      WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                ex.getCause().getMessage(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                     HttpHeaders headers,
                                                     HttpStatus status,
                                                     WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
