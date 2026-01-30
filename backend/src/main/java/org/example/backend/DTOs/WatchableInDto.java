package org.example.backend.dtos;

import org.example.backend.models.Watchable;

import java.time.LocalDate;
import java.util.List;

public record WatchableInDto(String title, List<String> actors, String duration, List<String> directors, LocalDate releaseDate, List<String> genres, int episode, int ageRating) {

}
