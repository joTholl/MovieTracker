package org.example.backend.services;

import lombok.AllArgsConstructor;
import org.example.backend.dtos.*;
import org.example.backend.dtos.SeriesSeasonIdDto;
import org.example.backend.dtos.SeasonInDto;
import org.example.backend.exceptions.ArgumentMismatchException;
import org.example.backend.helpers.UtilityFunctions;
import org.example.backend.models.Season;
import org.example.backend.models.Series;
import org.example.backend.repositories.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final SeasonService seasonService;
    private final UtilityFunctions utilityFunctions;

    public List<Series> getAllSeries() {
        List<SeriesSeasonIdDto> SeriesseasonIdDtos = seriesRepository.findAll();
        List<Series> series = new ArrayList<>();
        for (SeriesSeasonIdDto swid : SeriesseasonIdDtos) {
            series.add(new Series(swid, seasonService));
        }
        return series;
    }

    public Series getSeriesById(String id) throws NoSuchElementException {
        SeriesSeasonIdDto ssid = seriesRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Id not found"));
        return new Series(ssid, seasonService);
    }

    public Series createSeries(SeriesInDto seriesInDTO) {

        String id = utilityFunctions.createId();
        List<Season> seasons = new ArrayList<>();
        for (SeasonInDto seasonInDto : seriesInDTO.seasonInDtos()) {
            Season w = seasonService.createSeason(seasonInDto);
            seasons.add(w);
        }
        Series Series = new Series(id, seriesInDTO.title(), seasons, seriesInDTO.imageUrl());
        seriesRepository.save(new SeriesSeasonIdDto(Series));
        return Series;
    }


    public Series updateSeries(String id, SeriesUpdateDto seriesUpdateDTO) throws NoSuchElementException, ArgumentMismatchException {
        SeriesSeasonIdDto ssid = seriesRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Id not found"));
        List<Season> seasons = new ArrayList<>();
        List<String> seasonIds = new ArrayList<>();
        for (Season season : seriesUpdateDTO.seasons()) {
            if (ssid.seasonIds().contains(season.id())) {
                seasons.add(seasonService.updateSeason(season.id(), new SeasonUpdateDto(season)));
                seasonIds.add(season.id());
            } else {
                seasons.add(seasonService.createSeason(new SeasonInDto(season)));
                seasonIds.add(seasons.getLast().id());
            }

        }
        for (String seasonId : ssid.seasonIds()) {
            if (!seasonIds.contains(seasonId)) {
                seasonService.deleteSeason(seasonId);
            }
        }
        seriesRepository.save(new SeriesSeasonIdDto(id,seriesUpdateDTO.title(), seasonIds,seriesUpdateDTO.imageUrl()));
        return new Series(id, seriesUpdateDTO.title(), seasons, seriesUpdateDTO.imageUrl());
    }

    public void deleteSeries(String id) {
        if (seriesRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Id not found");
        }
        for (Season season : getSeriesById(id).seasons()) {
            seasonService.deleteSeason(season.id());
        }
        seriesRepository.deleteById(id);
    }
}


