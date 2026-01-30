package org.example.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends NoSuchElementException {


    public MovieNotFoundException(String id) {
        super("Movie mit ID " + id + " nicht gefunden");
    }
}
