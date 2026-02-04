package org.example.backend.dtos;

import java.util.List;

public record SeriesInDto(String title, List<SeasonInDto> seasonInDtos, String imageUrl) {
}
