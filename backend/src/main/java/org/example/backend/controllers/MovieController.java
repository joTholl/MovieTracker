package org.example.backend.controllers;

import org.example.backend.models.Movie;
import org.example.backend.models.MovieDto;
import org.example.backend.services.MovieService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable String id) {
        return service.getMovieById(id);
    }

    /**
    @GetMapping("/title/{title}")
    public List<Movie> getMoviesByTitle(@PathVariable String title) {
        return service.getMoviesByTitle(title);
    }

    @GetMapping("/actor/{actor}")
    public List<Movie> getMoviesByActor(@PathVariable String actor) {
        return service.getMoviesByActor(actor);
    }

    @GetMapping("/regisseur/{regisseur}")
    public List<Movie> getMoviesByRegisseur(@PathVariable String regisseur) {
        return service.getMoviesByRegisseur(regisseur);
    }

    @GetMapping("/release/{release}")
    public List<Movie> getMoviesByRelease(@PathVariable LocalDateTime release) {
        return service.getMoviesByRelease(release);
    }

    @GetMapping("/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable String genre) {
        return service.getMoviesByGenre(genre);
    }

    @GetMapping("/age/{age}")
    public List<Movie> getMoviesByminAge(@PathVariable int age) {
        return service.getMoviesByminAge(age);
    }
     **/

    @PostMapping
    public Movie createMovie(@RequestBody MovieDto movie) {
        return service.createMovie(movie);
    }

    @PutMapping("/{id}")
    public Movie changeMovie(@PathVariable String id, @RequestBody MovieDto movie) {
        return service.changeMovie(id, movie);
    }

    @DeleteMapping("/{id}")
    public boolean deleteMovie(@PathVariable String id) {
        return service.deleteMovie(id);
    }
}
