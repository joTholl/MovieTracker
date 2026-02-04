package org.example.backend.controllers;

import org.example.backend.dtos.MovieSeriesInDto;
import org.example.backend.dtos.MovieSeriesOutDto;
import org.example.backend.dtos.MovieSeriesUpdateDto;
import org.example.backend.models.MovieSeries;
import org.example.backend.services.MovieSeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public MovieSeriesOutDto getById(@PathVariable String id){
       return movieSeriesService.getById(id);
    }

    @PostMapping
    public ResponseEntity<MovieSeriesOutDto> create(@RequestBody MovieSeriesInDto inDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSeriesService.create(inDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieSeriesOutDto> update(@PathVariable String id, @RequestBody  MovieSeriesUpdateDto updateDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSeriesService.update(id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){

        if (movieSeriesService.deleteById(id)){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
