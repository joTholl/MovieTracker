package org.example.backend.controllers;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.SeasonInDTO;
import org.example.backend.models.Season;
import org.example.backend.services.SeasonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seasons")
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
    @ResponseStatus(HttpStatus.CREATED)
    public Season createSeason(@RequestBody SeasonInDTO seasonInDTO) {
        return seasonService.createSeason(seasonInDTO);
    }

    @PutMapping("/{id}")
    public Season updateSeason(@PathVariable String id, @RequestBody SeasonInDTO seasonInDTO){
        return seasonService.updateSeason(id, seasonInDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeason(@PathVariable String id){
        seasonService.deleteSeason(id);
    }
}
