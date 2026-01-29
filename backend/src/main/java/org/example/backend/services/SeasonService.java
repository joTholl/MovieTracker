package org.example.backend.services;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.SeasonInDTO;
import org.example.backend.DTOs.SeasonWatchableIdDTO;
import org.example.backend.exceptions.ArgumentMismatchException;
import org.example.backend.helpers.UtilityFunctions;
import org.example.backend.models.InWatchableDto;
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
        List<SeasonWatchableIdDTO> seasonWatchableIdDTOs = seasonRepository.findAll();
        List<Season> seasons = new ArrayList<>();
        for (SeasonWatchableIdDTO swid : seasonWatchableIdDTOs) {
            seasons.add(new Season(swid, watchableService));
        }
        return seasons;
    }

    public Season getSeasonById(String id) throws NoSuchElementException {
        SeasonWatchableIdDTO swid = seasonRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Id not found"));
        return new Season(swid, watchableService);
    }

    public Season createSeason(SeasonInDTO seasonInDTO) {
        String id = utilityFunctions.createId();
        Season season = new Season(id,seasonInDTO);
        for (Watchable watchable : season.watchables()) {
            watchableService.create(new InWatchableDto(watchable));
        }
        SeasonWatchableIdDTO swid = seasonRepository.save(new SeasonWatchableIdDTO(season));
        return getSeasonById(swid.id());
    }

    public Season updateSeason(String id, SeasonInDTO seasonInDTO) throws NoSuchElementException, ArgumentMismatchException {
        if (seasonRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Id not found");
        }
        for (Watchable watchable : seasonInDTO.watchables()) {
            watchableService.update(watchable.id(), new InWatchableDto(watchable));
        }
        Season season = new Season(id, seasonInDTO);
        SeasonWatchableIdDTO swid = seasonRepository.save(new SeasonWatchableIdDTO(season));
        return getSeasonById(swid.id());
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
