package org.example.backend.services;

import lombok.AllArgsConstructor;
import org.example.backend.dtos.SeasonInDto;
import org.example.backend.dtos.SeasonUpdateDto;
import org.example.backend.dtos.SeasonWatchableIdDto;
import org.example.backend.exceptions.ArgumentMismatchException;
import org.example.backend.helpers.UtilityFunctions;
import org.example.backend.dtos.WatchableInDto;
import org.example.backend.models.Season;
import org.example.backend.models.Watchable;
import org.example.backend.repositories.SeasonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final WatchableService watchableService;
    private final UtilityFunctions utilityFunctions;

    public List<Season> getAllSeasons() {
        List<SeasonWatchableIdDto> seasonWatchableIdDtos = seasonRepository.findAll();
        List<Season> seasons = new ArrayList<>();
        for (SeasonWatchableIdDto swid : seasonWatchableIdDtos) {
            seasons.add(new Season(swid, watchableService));
        }
        return seasons;
    }

    public Season getSeasonById(String id) throws NoSuchElementException {
        SeasonWatchableIdDto swid = seasonRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Id not found"));
        return new Season(swid, watchableService);
    }

    public Season createSeason(SeasonInDto seasonInDTO) {

        String id = utilityFunctions.createId();
        List<Watchable> watchables = new ArrayList<>();
        for (WatchableInDto watchableInDto : seasonInDTO.watchableInDtos()) {
            Watchable w = watchableService.create(watchableInDto);
            watchables.add(w);
        }
        Season season = new Season(id, seasonInDTO.seasonNumber(), watchables, seasonInDTO.streamables());
        seasonRepository.save(new SeasonWatchableIdDto(season));
        return season;
    }


    public Season updateSeason(String id, SeasonUpdateDto seasonUpdateDTO) throws NoSuchElementException, ArgumentMismatchException {
        SeasonWatchableIdDto swid = seasonRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Id not found"));
        List<Watchable> watchables = new ArrayList<>();
        List<String> watchableIds = new ArrayList<>();
        for (Watchable watchable : seasonUpdateDTO.watchables()) {
            if (swid.watchablesId().contains(watchable.id())) {
                watchables.add(watchableService.update(watchable.id(), new WatchableInDto(watchable)));
            } else {
                watchables.add(watchableService.create(new WatchableInDto(watchable)));
            }
            watchableIds.add(watchable.id());
        }
        for (String watchableId : swid.watchablesId()) {
            if (!watchableIds.contains(watchableId)) {
                watchableService.deleteById(watchableId);
            }
        }
        seasonRepository.save(new SeasonWatchableIdDto(id,seasonUpdateDTO.seasonNumber(), watchableIds,seasonUpdateDTO.streamables()));
        return new Season(id, seasonUpdateDTO.seasonNumber(), watchables, seasonUpdateDTO.streamables());
    }

    public void deleteSeason(String id) {
        if (seasonRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Id not found");
        }
        for (Watchable watchable : getSeasonById(id).watchables()) {
            watchableService.deleteById(watchable.id());
        }
        seasonRepository.deleteById(id);
    }
}
