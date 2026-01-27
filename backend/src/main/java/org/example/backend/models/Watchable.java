package org.example.backend.models;

import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Watchable")
@With
public record Watchable(@Id String id, String title, List<String> actors, String duration, List<String> directors, String releaseDate, List<String> genres, int episode, int ageRating) {
}
