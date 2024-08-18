package org.user.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;



/**
 * Global Exception Handler to manage exceptions across the application.
 */
@ControllerAdvice
public class EmpExceptionHandler {

    /**
     * Handles validation errors that occur during request processing.
     *
     * @param ex The MethodArgumentNotValidException instance containing validation errors.
     * @param request The WebRequest instance containing request details.
     * @return A ResponseEntity containing validation error details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        // Create a map to hold field names and their validation error messages.
        Map<String, String> response = new HashMap<>();

        // Iterate through all validation errors.
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // Extract the field name and error message.
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            // Populate the response map with field names and error messages.
            response.put(fieldName, message);
        });

        // Return a ResponseEntity with BAD_REQUEST status and the validation error details.
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handles exceptions of type EmployeeNotFoundException.
     * This method is invoked when an EmployeeNotFoundException is thrown by any controller method.
     *
     * @param exception The exception instance that was thrown.
     * @return A ResponseEntity containing a map with the error message and an HTTP status code.
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        // Create a map to hold the error message
        Map<String, String> response = new HashMap<>();
        
        // Put the exception message into the response map
        response.put("message", exception.getMessage());
        
        // Return a ResponseEntity with the error message and an HTTP status of EXPECTATION_FAILED
        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Handles general exceptions that occur throughout the application.
     *
     * @param ex The exception instance.
     * @param request The WebRequest instance containing request details.
     * @return A ResponseEntity containing a generic error message.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        // Create a map to hold a generic error message.
        Map<String, String> response = new HashMap<>();
        response.put("error", "An unexpected error occurred. Please try again later.");

        // Return a ResponseEntity with INTERNAL_SERVER_ERROR status and the error details.
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    
    /**
     * Error response structure used for returning error messages.
     */
    @Data
    @NoArgsConstructor
    public static class ErrorResponse {
        private String error;
        private String message;

    }
}
