package org.example.backend.services;


import org.example.backend.exceptions.WatchableNotFoundException;
import org.example.backend.models.Watchable;
import org.example.backend.repos.WatchableRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                .orElseThrow(() -> new WatchableNotFoundException(id));
    }

    public Watchable create(Watchable watchable) {
        return watchableRepo.save(watchable);
    }

    public void deleteById(String id) {
        boolean exists = watchableRepo.existsById(id);
        if (!exists) {
            throw new WatchableNotFoundException(id);
        }

        watchableRepo.deleteById(id);
    }

    public Watchable update(String id, Watchable watchable) {
        boolean exists = watchableRepo.existsById(id);

        if (!exists) {
            throw new WatchableNotFoundException(id);
        }

        Watchable existing = watchableRepo.findById(id).orElseThrow(() -> new WatchableNotFoundException(id));
        Watchable toSave = existing
                .withId(id)
                .withTitle(watchable.title())
                .withActors(watchable.actors())
                .withDirectors(watchable.directors())
                .withDuration(watchable.duration())
                .withGenres(watchable.genres())
                .withReleaseDate(watchable.releaseDate())
                .withEpisode(watchable.episode())
                .withAgeRating(watchable.ageRating());

        return watchableRepo.save(toSave);
    }
}