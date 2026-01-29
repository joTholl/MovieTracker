package org.example.backend.models;

import lombok.With;
import org.example.backend.dtos.SeasonInDTO;
import org.example.backend.dtos.SeasonWatchableIdDTO;
import org.example.backend.services.WatchableService;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@With
public record Season(@Id String id, int seasonNumber, List<Watchable> watchables, List<String> streamables) {

    public Season(SeasonWatchableIdDTO swid, WatchableService watchableService) {
        List<Watchable> watchables = new ArrayList<>();
        for (String watchableId : swid.watchablesId()) {
            Watchable watchable = watchableService.getById(watchableId);
            watchables.add(watchable);
        }
        this(swid.id(), swid.seasonNumber(), watchables, swid.streamables());
    }

    public Season(String id, SeasonInDTO seasonInDTO) {
        this(id, seasonInDTO.seasonNumber(), seasonInDTO.watchables(), seasonInDTO.streamables());

    }
}
