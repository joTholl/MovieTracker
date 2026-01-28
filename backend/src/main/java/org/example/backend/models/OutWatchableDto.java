package org.example.backend.models;

import org.springframework.data.annotation.Id;

import java.util.List;

public record OutWatchableDto(@Id String id, String title, List<String> actors, String duration, List<String> directors, String releaseDate, List<String> genres, int episode, int ageRating) {
}
