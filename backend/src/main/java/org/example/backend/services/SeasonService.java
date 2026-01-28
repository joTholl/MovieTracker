package org.example.backend.services;

import lombok.AllArgsConstructor;
import org.example.backend.exceptions.ArgumentMismatchException;
import org.example.backend.models.Season;
import org.example.backend.repositories.SeasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;

    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    public Season getSeasonById(String id) throws NoSuchElementException {
        return seasonRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Id not found"));
    }

    public Season createSeason(Season season) {
        return seasonRepository.save(season);
    }

    public Season updateSeason(String id, Season season) throws NoSuchElementException, ArgumentMismatchException {
        if (seasonRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Id not found");
        } else if (!id.equals(season.id())) {
            throw new ArgumentMismatchException("Ids are not equal");
        }
        return seasonRepository.save(season);
    }

    public void deleteSeason(String id) {
        if (seasonRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Id not found");
        }
        seasonRepository.deleteById(id);
    }
}
