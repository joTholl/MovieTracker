package org.example.backend.services;

import org.example.backend.exceptions.WatchableNotFoundException;
import org.example.backend.helpers.UtilityFunctions;
import org.example.backend.models.InWatchableDto;
import org.example.backend.models.Watchable;
import org.example.backend.repos.WatchableRepo;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new WatchableNotFoundException(id));
    }

    public Watchable create(InWatchableDto in) {

        UtilityFunctions utilityFunctions = new UtilityFunctions();
        String id = utilityFunctions.createId();

        Watchable newWatchable = new Watchable(
                id,
                in.title(),
                in.actors(),
                in.duration(),
                in.directors(),
                in.releaseDate(),
                in.genres(),
                in.episode(),
                in.ageRating());

        return watchableRepo.save(newWatchable);
    }

    public void deleteById(String id) {

        boolean exists = watchableRepo.existsById(id);

        if (!exists) {
            throw new WatchableNotFoundException(id);
        }
        watchableRepo.deleteById(id);
    }

    public Watchable update(String id, InWatchableDto in) {
        boolean exists = watchableRepo.existsById(id);

        if (!exists) {
            throw new WatchableNotFoundException(id);
        }

        Watchable existing = watchableRepo.findById(id).orElseThrow(() -> new WatchableNotFoundException(id));

        Watchable toSave = existing
                .withId(id)
                .withTitle(in.title())
                .withActors(in.actors())
                .withDirectors(in.directors())
                .withDuration(in.duration())
                .withGenres(in.genres())
                .withReleaseDate(in.releaseDate())
                .withEpisode(in.episode())
                .withAgeRating(in.ageRating());

        return watchableRepo.save(toSave);
    }
}