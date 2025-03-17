package com.example.bookstore.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        ErrorDetail error = new ErrorDetail(entityNotFoundException.getMessage());
        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityAlreadyExistsException(EntityExistsException entityExistsException) {
        ErrorDetail errorDetail = new ErrorDetail(entityExistsException.getMessage());
        return new ResponseEntity<>(errorDetail, CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException) {
        ErrorDetail errorDetail = new ErrorDetail(invalidPasswordException.getMessage());
        return new ResponseEntity<>(errorDetail, BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotVerifiedException.class)
    public ResponseEntity<?> handleAccountNotVerifiedException(AccountNotVerifiedException accountNotVerifiedException) {
        ErrorDetail errorDetail = new ErrorDetail(accountNotVerifiedException.getMessage());
        return new ResponseEntity<>(errorDetail, BAD_REQUEST);
    }

    @ExceptionHandler(NoExemplaryAvailableException.class)
    public ResponseEntity<?> handleNoExemplaryAvailableException(NoExemplaryAvailableException ex) {
        ErrorDetail error = new ErrorDetail(ex.getMessage());
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(MissingArgumentException.class)
    public ResponseEntity<?> handleMissingArgumentException(MissingArgumentException ex) {
        ErrorDetail error = new ErrorDetail(ex.getMessage());
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String property = getProperty(error);
            String message = error.getDefaultMessage();
            errors.put(property, message);
        });
        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    private String getProperty(ObjectError objectError) {
        if (objectError instanceof FieldError) {
            return ((FieldError) objectError).getField();
        }
        return objectError.getObjectName();
    }
}