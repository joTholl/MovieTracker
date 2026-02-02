package org.example.backend.dtos;

import lombok.With;
import org.example.backend.models.Movie;

import java.util.List;

@With
public record MovieSeriesInDto(String title, List<String> movieIds) {
}
