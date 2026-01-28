package org.example.backend.controllers;

import org.example.backend.models.Movie;
import org.example.backend.services.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/movie")
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }

    @GetMapping("/movie/{id}")
    public Movie getMovieById(@PathVariable String id) {
        return service.getMovieById(id);
    }

    @GetMapping("/movie/title/{title}")
    public List<Movie> getMoviesByTitle(@PathVariable String title) {
        return service.getMoviesByTitle(title);
    }

    @GetMapping("/movie/actor/{actor}")
    public List<Movie> getMoviesByActor(@PathVariable String actor) {
        return service.getMoviesByActor(actor);
    }

    @GetMapping("/movie/regisseur/{regisseur}")
    public List<Movie> getMoviesByRegisseur(@PathVariable String regisseur) {
        return service.getMoviesByRegisseur(regisseur);
    }

    @GetMapping("/movie/release/{release}")
    public List<Movie> getMoviesByRelease(@PathVariable LocalDateTime release) {
        return service.getMoviesByRelease(release);
    }

    @GetMapping("/movie/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable String genre) {
        return service.getMoviesByGenre(genre);
    }

    @GetMapping("/movie/age/{age}")
    public List<Movie> getMoviesByminAge(@PathVariable int age) {
        return service.getMoviesByminAge(age);
    }
}
