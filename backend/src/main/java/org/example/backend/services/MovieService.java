package org.example.backend.services;

import org.example.backend.models.Movie;
import org.example.backend.repositorys.MovieRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepo repo;

    public MovieService(MovieRepo repo) {
        this.repo = repo;
    }

    public List<Movie> getAllMovies() {
        return repo.findAll();
    }

    public Movie getMovieById(String id) {
        return repo.findById(id).get();
    }

    public List<Movie> getMoviesByTitle(String title) {
        return null;
    }

    public List<Movie> getMoviesByActor(String actor) {
        return null;
    }

    public List<Movie> getMoviesByRegisseur(String regisseur) {

        return null;
    }

    public List<Movie> getMoviesByRelease(LocalDateTime release) {

        return null;
    }

    public List<Movie> getMoviesByGenre(String genre) {

        return null;
    }

    public List<Movie> getMoviesByminAge(int age) {

        return null;
    }
}
