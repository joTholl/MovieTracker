package org.example.backend.controllers;

import lombok.AllArgsConstructor;
import org.example.backend.dtos.SeasonInDto;
import org.example.backend.dtos.SeasonUpdateDto;
import org.example.backend.models.Season;
import org.example.backend.services.SeasonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seasons")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
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
    public Season createSeason(@RequestBody SeasonInDto seasonInDTO) {
        return seasonService.createSeason(seasonInDTO);
    }

    @PutMapping("/{id}")
    public Season updateSeason(@PathVariable String id, @RequestBody SeasonUpdateDto seasonUpdateDTO){
        return seasonService.updateSeason(id, seasonUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeason(@PathVariable String id){
        seasonService.deleteSeason(id);
    }
}
