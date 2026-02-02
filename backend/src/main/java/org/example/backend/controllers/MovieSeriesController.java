package org.example.backend.controllers;

import org.example.backend.dtos.MovieSeriesInDto;
import org.example.backend.dtos.MovieSeriesOutDto;
import org.example.backend.models.MovieSeries;
import org.example.backend.services.MovieSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public MovieSeriesOutDto create(@RequestBody MovieSeriesInDto inDto){
       return movieSeriesService.create(inDto);
    }
}
