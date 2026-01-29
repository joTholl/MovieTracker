package org.example.backend.exceptions;

public class IdIsNullException extends IllegalArgumentException {

    public IdIsNullException(String ref) {
        super(ref + ": id is null exception");

    }
}
