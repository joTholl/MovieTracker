package org.example.backend.services;

import org.example.backend.dtos.FilterDto;
import org.example.backend.exceptions.IdIsNullException;
import org.example.backend.exceptions.WatchableNotFoundException;
import org.example.backend.helpers.UtilityFunctions;
import org.example.backend.dtos.WatchableInDto;
import org.example.backend.models.Watchable;
import org.example.backend.repositories.WatchableRepository;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
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

    public List<Watchable> getAllByTitle(List<Watchable> watchables, String title) {

        String normalizedTitle = title.trim().toLowerCase();
        return watchables
                .stream().filter(watchable -> watchable.title().trim().toLowerCase().contains(normalizedTitle)).toList();
    }

    public List<Watchable> getAllByActor(List<Watchable> watchables, String actorName) {

        String actorNameNormalized = actorName.trim().toLowerCase();

        return watchables
                .stream()
                .filter(watchable -> watchable.actors()
                        .stream()
                        .anyMatch(actor -> actor.trim().toLowerCase().contains(actorNameNormalized)))
                .toList();
    }

    public List<Watchable> getAllByDirector(List<Watchable> watchables, String directorName) {

        String directorNameNormalized = directorName.trim().toLowerCase();

        return watchables
                .stream()
                .filter(watchable -> watchable.directors()
                        .stream()
                        .anyMatch(director -> director.trim().toLowerCase().contains(directorNameNormalized)))
                .toList();
    }

    public List<Watchable> getAllByGenre(List<Watchable> watchables, String inGenre) {
        String genreNameNormalized = inGenre.trim().toLowerCase();

        return watchables
                .stream()
                .filter(watchable -> watchable.genres()
                        .stream()
                        .anyMatch(genre -> genre.trim().toLowerCase().contains(genreNameNormalized)))
                .toList();
    }

    public List<Watchable> getAllByReleaseYear(int year) {
        return watchableRepository.findAll()
                .stream().filter(watchable -> watchable.releaseDate().getYear() == year).toList();
    }

    public List<Watchable> getAllBySingleFilter(List<Watchable> watchables, FilterDto filterDto) {

        if (filterDto == null) {
            throw new InvalidParameterException("cannot get watchable by single filter: filter or input is null");
        }

        return switch (filterDto.searchFilter()) {
            case TITLE -> getAllByTitle(watchables, filterDto.input());
            case ACTORS -> getAllByActor(watchables, filterDto.input());
            case DIRECTORS -> getAllByDirector(watchables, filterDto.input());
            case GENRES -> getAllByGenre(watchables, filterDto.input());
            default -> throw new IllegalArgumentException("cannot get watchable by single filter: Invalid filter");
        };
    }

    public List<Watchable> getAllByMultipleFilters(List<FilterDto> filterDtos) {

        if (filterDtos == null) {
            throw new InvalidParameterException("cannot get watchable by single filter: filter or input is null");
        }

        List<Watchable> watchables = watchableRepository.findAll();
        if (!filterDtos.isEmpty()) {
            for (FilterDto filterDto : filterDtos) {
                watchables = getAllBySingleFilter(watchables, filterDto);
            }
        }

        return watchables;
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