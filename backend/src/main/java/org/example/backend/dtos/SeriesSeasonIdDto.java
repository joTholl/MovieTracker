package org.example.backend.dtos;

import org.example.backend.models.Season;
import org.example.backend.models.Series;

import java.util.ArrayList;
import java.util.List;

public record SeriesSeasonIdDto(String id, String title, List<String> seasonIds, String imageUrl) {

    public SeriesSeasonIdDto(Series series){
        List<String> seasonIds = new ArrayList<>();
        for (Season season : series.seasons()){
            seasonIds.add(season.id());
        }
        this(series.id(), series.title(), seasonIds, series.imageUrl());
    }
}
