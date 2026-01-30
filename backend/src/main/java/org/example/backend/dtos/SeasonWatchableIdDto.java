package org.example.backend.dtos;

import lombok.With;
import org.example.backend.models.Season;
import org.example.backend.models.Watchable;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@With
public record SeasonWatchableIdDto(@Id String id, int seasonNumber, List<String> watchablesId, List<String> streamables) {

    public SeasonWatchableIdDto(Season season) {
        List<String> watchablesId = new ArrayList<>();
        for (Watchable watchable : season.watchables()) {
            watchablesId.add(watchable.id());
        }
        this(season.id(), season.seasonNumber(), watchablesId, season.streamables());
    }

}
