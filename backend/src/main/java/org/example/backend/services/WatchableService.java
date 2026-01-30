package org.example.backend.services;

import org.example.backend.exceptions.WatchableNotFoundException;
import org.example.backend.helpers.UtilityFunctions;
import org.example.backend.dtos.WatchableInDto;
import org.example.backend.models.Watchable;
import org.example.backend.repositories.WatchableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchableService {

    private final WatchableRepository watchableRepository;

    public WatchableService(WatchableRepository watchableRepository) {
        this.watchableRepository = watchableRepository;
    }

    public List<Watchable> getAll() {
        return watchableRepository.findAll();
    }

    public Watchable getById(String id) {
        return watchableRepository.findById(id)
                .orElseThrow(() -> new WatchableNotFoundException(id));
    }

    public Watchable create(WatchableInDto in) {

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

        return watchableRepository.save(newWatchable);
    }

    public void deleteById(String id) {

        boolean exists = watchableRepository.existsById(id);

        if (!exists) {
            throw new WatchableNotFoundException(id);
        }
        watchableRepository.deleteById(id);
    }

    public Watchable update(String id, WatchableInDto in) {
        boolean exists = watchableRepository.existsById(id);

        if (!exists) {
            throw new WatchableNotFoundException(id);
        }

        Watchable existing = watchableRepository.findById(id).orElseThrow(() -> new WatchableNotFoundException(id));

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

        return watchableRepository.save(toSave);
    }
}