package org.example.backend.controllers;

import org.example.backend.dtos.MovieSeriesInDto;
import org.example.backend.dtos.MovieSeriesOutDto;
import org.example.backend.dtos.MovieSeriesUpdateDto;
import org.example.backend.models.MovieSeries;
import org.example.backend.services.MovieSeriesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movieseries")
public class MovieSeriesController {

    private MovieSeriesService movieSeriesService;
    public MovieSeriesController(MovieSeriesService movieSeriesService) {
        this.movieSeriesService = movieSeriesService;
    }

    @GetMapping
    public List<MovieSeries> getAll(){
        return movieSeriesService.findAll();
    }

    @GetMapping
    public MovieSeriesOutDto getById(@RequestParam String id){
       return movieSeriesService.getById(id);
    }

    @PostMapping
    public MovieSeriesOutDto create(@RequestBody MovieSeriesInDto inDto){
       return movieSeriesService.create(inDto);
    }

    @PutMapping
    public MovieSeriesOutDto update(@RequestBody String id, MovieSeriesUpdateDto updateDto){
        return movieSeriesService.update(id,  updateDto);
    }

    @DeleteMapping
    public void delete(@RequestParam String id){
        movieSeriesService.delete(id);
    }
}
