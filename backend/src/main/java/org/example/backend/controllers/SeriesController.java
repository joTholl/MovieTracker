package org.example.backend.controllers;

import lombok.AllArgsConstructor;
import org.example.backend.dtos.SeriesInDto;
import org.example.backend.dtos.SeriesUpdateDto;
import org.example.backend.models.Series;
import org.example.backend.services.SeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/series")
@RestController
@AllArgsConstructor
public class SeriesController {

    private final SeriesService seriesService;

    @GetMapping
    public List<Series> getAllSeasons() {
        return seriesService.getAllSeries();
    }

    @GetMapping("/{id}")
    public Series getSeasonById(@PathVariable String id) {
        return seriesService.getSeriesById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Series createSeason(@RequestBody SeriesInDto seriesInDTO) {
        return seriesService.createSeries(seriesInDTO);
    }

    @PutMapping("/{id}")
    public Series updateSeason(@PathVariable String id, @RequestBody SeriesUpdateDto seriesUpdateDTO){
        return seriesService.updateSeries(id, seriesUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeason(@PathVariable String id){
        seriesService.deleteSeries(id);
    }
}
