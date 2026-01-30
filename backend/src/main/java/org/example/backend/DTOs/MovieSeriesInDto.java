package org.example.backend.dtos;

import lombok.With;

@With
public record MovieSeriesInDto(String title, String[] movieIds) {
}
