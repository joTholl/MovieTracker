package org.example.backend.dtos;

import lombok.With;

import java.util.List;

@With
public record SeasonInDto(int seasonNumber, List<WatchableInDto> watchableInDtos, List<String> streamables) {
}
