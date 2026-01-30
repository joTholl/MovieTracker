package org.example.backend.exceptions;

import java.util.NoSuchElementException;

public class WatchableNotFoundException extends NoSuchElementException {
    public WatchableNotFoundException(String id) {

        super("Watchable not found with id: " + id + " no such Element exists");
    }
}
