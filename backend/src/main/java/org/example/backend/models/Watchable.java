package org.example.backend.models;

import lombok.With;
import org.springframework.data.annotation.Id;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@With
public record Watchable(@Id String id, String title, List<String> actors, String duration, List<String> directors, LocalDateTime releaseDate, List<String> genres, int episode, int ageRating) {
}
