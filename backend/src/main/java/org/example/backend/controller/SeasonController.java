package org.example.backend.controller;

import lombok.AllArgsConstructor;
import org.example.backend.exceptions.ArgumentMismatchException;
import org.example.backend.models.Season;
import org.example.backend.services.SeasonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/season")
@AllArgsConstructor
public class SeasonController {

    private final  SeasonService seasonService;

    @GetMapping
    public List<Season> getAllSeasons() {
        return seasonService.getAllSeasons();
    }

    @GetMapping("/{id}")
    public Season getSeasonById(@PathVariable String id) {
        return seasonService.getSeasonById(id);
    }

    @PostMapping
    public Season createSeason(@RequestBody Season season) {
        return seasonService.createSeason(season);
    }

    @PutMapping("/{id}")
    public Season updateSeason(@PathVariable String id, @RequestBody Season season){
        return seasonService.updateSeason(id, season);
    }

    @DeleteMapping("/{id}")
    public void deleteSeason(@PathVariable String id){
        seasonService.deleteSeason(id);
    }
}
