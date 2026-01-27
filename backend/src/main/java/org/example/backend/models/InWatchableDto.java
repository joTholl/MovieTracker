package org.example.backend.models;

import java.util.List;

public record InWatchableDto(String title, List<String> actors, String duration, List<String> directors, String releaseDate, List<String> genres, int episode, int ageRating) {
}
