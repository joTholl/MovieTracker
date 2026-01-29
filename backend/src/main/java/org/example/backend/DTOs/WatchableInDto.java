package org.example.backend.DTOs;

import java.time.LocalDate;
import java.util.List;

public record WatchableInDto(String title, List<String> actors, String duration, List<String> directors, LocalDate releaseDate, List<String> genres, int episode, int ageRating) {

}
