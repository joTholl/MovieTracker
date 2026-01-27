package org.example.backend.services;


import org.example.backend.models.Watchable;
import org.example.backend.repos.WatchableRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchableService {

    private final WatchableRepo watchableRepository;

    public WatchableService(WatchableRepo watchableRepository) {
        this.watchableRepository = watchableRepository;
    }

    // Returns all Watchable documents from MongoDB.
    public List<Watchable> getAll() {
        return watchableRepository.findAll();
    }
}