package org.example.backend.dtos;

import org.example.backend.models.Season;
import org.example.backend.models.Series;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document(collection = "series")
public record SeriesSeasonIdDto(@Id String id, String title, List<String> seasonIds, String thumbnail) {

    public SeriesSeasonIdDto(Series series){
        List<String> seasonIds = new ArrayList<>();
        for (Season season : series.seasons()){
            seasonIds.add(season.id());
        }
        this(series.id(), series.title(), seasonIds, series.thumbnail());
    }
}
