package org.example.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.With;
import org.example.backend.dtos.WatchableInDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "Watchable")
@With
public record Watchable(
        @Id String id,
        String title,
        List<String> actors,
        String duration,
        List<String> directors,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate releaseDate,
        List<String> genres,
        int episode,
        int ageRating) {

        public Watchable(String id, WatchableInDto inDto) {
                this(
                        id,
                        inDto.title(),
                        inDto.actors(),
                        inDto.duration(),
                        inDto.directors(),
                        inDto.releaseDate(),
                        inDto.genres(),
                        inDto.episode(),
                        inDto.ageRating());
        }
}
