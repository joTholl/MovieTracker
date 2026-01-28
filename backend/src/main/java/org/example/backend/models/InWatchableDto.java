package org.example.backend.models;

import java.time.LocalDate;
import java.util.List;

public record InWatchableDto(String title, List<String> actors, String duration, List<String> directors, LocalDate releaseDate, List<String> genres, int episode, int ageRating) {

}
