package org.example.backend.DTOs;

import org.example.backend.models.Movie;
import org.example.backend.models.Watchable;
import org.example.backend.services.WatchableService;

import java.util.List;

public record MovieOutDto(String id, Watchable watchable, List<String> streamable) {

    public MovieOutDto(Movie movie, WatchableService service) {
        this(movie.id(), service.getById(movie.watchableID()), movie.streamable());
    }
}
