package org.example.backend.dtos;

import lombok.With;
import org.example.backend.models.Season;
import org.example.backend.models.Watchable;

import java.util.ArrayList;
import java.util.List;

@With
public record SeasonInDto(int seasonNumber, List<WatchableInDto> watchableInDtos, List<String> streamables) {

    public SeasonInDto(Season season) {
        List<WatchableInDto> watchableInDtos = new ArrayList<>();
        for (Watchable watchable: season.watchables()) {
            watchableInDtos.add(new WatchableInDto(watchable));
        }
        this(season.seasonNumber(), watchableInDtos, season.streamables());
    }
}
