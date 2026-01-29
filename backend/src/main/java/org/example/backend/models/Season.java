package org.example.backend.models;

import lombok.With;
import org.example.backend.DTOs.SeasonInDTO;
import org.example.backend.DTOs.SeasonWatchableIdDTO;
import org.example.backend.services.WatchableService;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@With
public record Season(@Id String id, int seasonNumber, List<Watchable> watchables, List<String> streamable) {

    public Season(SeasonWatchableIdDTO swid, WatchableService watchableService) {
        this(swid.id(), swid.seasonNumber(), new ArrayList<>(), swid.streamable());
        for (String watchableId : swid.watchablesId()) {
            Watchable watchable = watchableService.getById(watchableId);
            this.watchables.add(watchable);
        }
    }

    public Season(String id, SeasonInDTO seasonInDTO) {
        this(id, seasonInDTO.seasonNumber(), seasonInDTO.watchables(), seasonInDTO.streamable());

    }
}
