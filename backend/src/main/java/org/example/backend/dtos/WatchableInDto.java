package org.example.backend.dtos;

import lombok.With;
import org.example.backend.models.Watchable;

import java.time.LocalDate;
import java.util.List;

@With
public record WatchableInDto(String title, List<String> actors, String duration, List<String> directors, LocalDate releaseDate, List<String> genres, int episode, int ageRating) {

    public WatchableInDto(Watchable watchable){
        this(watchable.title(), watchable.actors(), watchable.duration(), watchable.directors(), watchable.releaseDate(), watchable.genres(), watchable.episode(), watchable.ageRating());
    }
}
