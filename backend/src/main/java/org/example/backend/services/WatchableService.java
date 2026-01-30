package org.example.backend.services;

import org.example.backend.exceptions.IdIsNullException;
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

        if (id == null) {
            throw new IdIsNullException(this.toString());
        }

        return watchableRepository.findById(id)
                .orElseThrow(() -> new WatchableNotFoundException(id));
    }

    public Watchable create(WatchableInDto in) {

        if (in == null) {
            throw new IllegalArgumentException(this.toString() + "WatchableInDto is null");
        }

        UtilityFunctions utilityFunctions = new UtilityFunctions();
        String id = utilityFunctions.createId();

        Watchable newWatchable = new Watchable(id, in);
        return watchableRepository.save(newWatchable);
    }

    public boolean deleteById(String id) {

        if(id == null){
            throw new IdIsNullException(this.toString());
        }

        if (!watchableRepository.existsById(id)) {
            throw new WatchableNotFoundException(id);
        }

        watchableRepository.deleteById(id);
        return true;
    }

    public Watchable update(String id, WatchableInDto in) {

        if (id == null){
            throw new IdIsNullException(this.toString());
        }

        if (in == null) {
            throw new IllegalArgumentException(this.toString() + "WatchableInDto is null");
        }

        Watchable existing = watchableRepository.findById(id).orElseThrow(() -> new WatchableNotFoundException(id));

        Watchable toSave = existing
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