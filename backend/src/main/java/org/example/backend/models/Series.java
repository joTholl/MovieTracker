package org.example.backend.models;

import lombok.With;
import org.example.backend.dtos.SeriesSeasonIdDto;
import org.example.backend.services.SeasonService;

import java.util.ArrayList;
import java.util.List;

@With
public record Series(String id, String title, List<Season> seasons, String imageUrl) {

    public Series(SeriesSeasonIdDto ssid, SeasonService seasonService) {
        List<Season> seasons = new ArrayList<>();
        for (String seasonId:ssid.seasonIds()){
            Season season = seasonService.getSeasonById(seasonId);
            seasons.add(season);
        }
        this(ssid.id(), ssid.title(),seasons, ssid.imageUrl());
    }
}
