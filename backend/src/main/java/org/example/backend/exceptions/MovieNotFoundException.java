package org.example.backend.exceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String id) {
        super("Movie mit ID " + id + " nicht gefunden");
    }
}
