package org.example.backend.exceptions;


public class ArgumentMismatchException extends RuntimeException {

    public ArgumentMismatchException() {
    }

    public ArgumentMismatchException(String message) {
        super(message);
    }
}
