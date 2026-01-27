package org.example.backend.services;


import org.example.backend.models.Watchable;
import org.example.backend.repos.WatchableRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WatchableService {

    private final WatchableRepo watchableRepo;

    public WatchableService(WatchableRepo watchableRepo) {
        this.watchableRepo = watchableRepo;
    }

    // Returns all Watchable documents from MongoDB.
    public List<Watchable> getAll() {
        return watchableRepo.findAll();
    }

    // Returns a Watchable by id, or throws 404 if it doesn't exist.
    public Watchable getById(String id) {
        return watchableRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Watchable with id '" + id + "' not found"
                ));
    }

    // Creates (saves) a Watchable in MongoDB and returns the saved document.
    public Watchable create(Watchable watchable) {
        return watchableRepo.save(watchable);
    }
}