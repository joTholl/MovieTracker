package org.example.backend.services;

import org.example.backend.dtos.*;
import org.example.backend.exceptions.IdIsNullException;
import org.example.backend.exceptions.WatchableNotFoundException;
import org.example.backend.helpers.UtilityFunctions;
import org.example.backend.models.Movie;
import org.example.backend.models.MovieSeries;
import org.example.backend.repositories.MovieSeriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieSeriesService {

    MovieSeriesRepository movieSeriesRepository;

    MovieService movieService;

    public MovieSeriesService(MovieSeriesRepository movieSeriesRepository, MovieService movieService) {
        this.movieSeriesRepository = movieSeriesRepository;
        this.movieService = movieService;
    }

    private List<Movie> GetMoviesById(List<String> movieIds) {

        if (movieIds == null || movieIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Movie> outMovies = new ArrayList<>();
        for (String movieId : movieIds) {
            outMovies.add(movieService.getMovieById(movieId));
        }

        return outMovies;
    }

    public List<MovieSeries> findAll() {

        return movieSeriesRepository.findAll();
    }

    public MovieSeriesOutDto getById(String id) {

        if (id == null) {
            return null;
        }

        MovieSeries movieSeries = movieSeriesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie Series with id " + id + " does not exist"));

        List<Movie> outMovies = new ArrayList<>();

        List<String> Ids = movieSeries.movieIds();
        if (!Ids.isEmpty()) {
            for (String movieId : Ids) {
                outMovies.add(movieService.getMovieById(movieId));
            }
        }

        return new MovieSeriesOutDto(movieSeries.id(), movieSeries.title(), outMovies);
    }

    public MovieSeriesOutDto create(MovieSeriesInDto inDto) {

        if (inDto == null) {
            return null;
        }

        UtilityFunctions utilityFunctions = new UtilityFunctions();
        String id = utilityFunctions.createId();

        List<Movie> outMovies = GetMoviesById(inDto.movieIds());

        movieSeriesRepository.save(new MovieSeries(id, inDto.title(), inDto.movieIds()));
        return new MovieSeriesOutDto(id, inDto.title(), outMovies);
    }

    public MovieSeriesOutDto update(String id, MovieSeriesUpdateDto updateDto) {

        if (updateDto == null) {
            return null;
        }

        MovieSeries toUpdate = movieSeriesRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("Movie Series with id " + id + " does not exist"));

        MovieSeries updated = toUpdate
                .withTitle(updateDto.title())
                .withMovieIds(updateDto.movieIds());

       List<Movie> outMovies = GetMoviesById(updateDto.movieIds());

        movieSeriesRepository.save(updated);
        return new MovieSeriesOutDto(updated.id(), updated.title(), outMovies);
    }

    public boolean deleteById(String id){

        if(id == null){
            throw new IllegalArgumentException("cannot delete MovieSeries with id null");
        }

        if (movieSeriesRepository.existsById(id)) {
            movieSeriesRepository.deleteById(id);
        }

        // return needs to be inversed to trigger the right Http Response when called by Controller
       return !movieSeriesRepository.existsById(id);
    }
}