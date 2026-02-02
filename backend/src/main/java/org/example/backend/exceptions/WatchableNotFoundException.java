package org.example.backend.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WatchableNotFoundException extends NoSuchElementException {
    public WatchableNotFoundException(String id) {

        super("Watchable not found with id: " + id + " no such Element exists");
    }
}
