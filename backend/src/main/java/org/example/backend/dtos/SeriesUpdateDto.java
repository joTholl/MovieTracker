package org.example.backend.dtos;

import org.example.backend.models.Season;

import java.util.List;

public record SeriesUpdateDto(String id, String title, List<Season> seasons, String thumbnail) {

}
