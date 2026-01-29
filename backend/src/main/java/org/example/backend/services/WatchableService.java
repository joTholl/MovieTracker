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
        if(id == null) {
            throw new RuntimeException("cannot find watchable with null id");
        }
        return watchableRepo.findById(id)
                .orElseThrow(() -> new WatchableNotFoundException(id));
    }

    public Watchable create(InWatchableDto in) {

        if (in == null) {
            throw new RuntimeException("cannot create Watchable with null InWatchableDto");
        }

        UtilityFunctions utilityFunctions = new UtilityFunctions();
        String id = utilityFunctions.createId();

        Watchable newWatchable = new Watchable(id, in);

        return watchableRepo.save(newWatchable);
    }

    public boolean deleteById(String id) {

        if(id == null){
            throw new RuntimeException("cannot delete Watchable with null id");
        }

        boolean exists = watchableRepo.existsById(id);
        if (!exists) {
            throw new WatchableNotFoundException(id);
        }

        watchableRepo.deleteById(id);
        return true;
    }

    public Watchable update(String id, InWatchableDto in) {

        if (in == null) {
            throw new RuntimeException("cannot update Watchable with null InWatchableDto");
        }

        boolean exists = watchableRepo.existsById(id);

        if (!exists) {
            throw new WatchableNotFoundException(id);
        }

        Watchable existing = watchableRepo.findById(id).orElseThrow(() -> new WatchableNotFoundException(id));

        Watchable toSave = existing
                //.withId(id)
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