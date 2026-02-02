package org.example.backend.dtos;

import org.example.backend.models.Movie;
import org.springframework.data.annotation.Id;

import java.util.List;

public record MovieSeriesUpdateDto(@Id String id, String title, List<String> movieIds) {
}
