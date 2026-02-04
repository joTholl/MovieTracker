package org.example.backend.dtos;

import lombok.With;
import org.example.backend.models.Season;
import org.example.backend.models.Watchable;

import java.util.List;

@With
public record SeasonUpdateDto(int seasonNumber, List<Watchable> watchables, List<String> streamables) {

    public SeasonUpdateDto(Season season){
        this(season.seasonNumber(), season.watchables(), season.streamables());
    }
}
