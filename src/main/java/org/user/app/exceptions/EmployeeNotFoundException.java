package org.user.app.exceptions;

/**
 * Custom exception to be thrown when an employee is not found.
 * Extends RuntimeException to provide unchecked exception handling.
 */
public class EmployeeNotFoundException extends RuntimeException {
    // Unique identifier for serialization purposes
    private static final long serialVersionUID = 1L;

    /**
     * Constructor to initialize the exception with a custom message.
     * 
     * @param message The detail message for the exception.
     */
    public EmployeeNotFoundException(String message) {
        super(message); // Pass the message to the superclass constructor
    }
}