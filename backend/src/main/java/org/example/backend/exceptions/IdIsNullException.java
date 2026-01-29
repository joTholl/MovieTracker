package org.example.backend.exceptions;

public class IdIsNullException extends RuntimeException {

    public IdIsNullException(String ref) {
        super(ref + ": id is null exception");

    }
}
