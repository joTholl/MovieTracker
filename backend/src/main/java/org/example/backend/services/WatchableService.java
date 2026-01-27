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

    public List<Watchable> getAll() {
        return watchableRepo.findAll();
    }

    public Watchable getById(String id) {
        return watchableRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Watchable with id '" + id + "' not found"
                ));
    }

    public Watchable create(Watchable watchable) {
        return watchableRepo.save(watchable);
    }
}