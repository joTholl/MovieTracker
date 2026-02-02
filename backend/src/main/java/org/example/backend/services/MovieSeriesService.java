package org.example.backend.services;


import org.example.backend.dtos.*;
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

    public List<MovieSeries> findAll() {
        return movieSeriesRepository.findAll();
    }

    public MovieSeriesOutDto findById(String id) {

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

//        List<String> movieIds = new ArrayList<>();
//        List<Movie> outMovies = new ArrayList<>();
//        if (!inDto.inMovies().isEmpty()) {
//
//            for (MovieInDto inMovie : inDto.inMovies()){
//               Movie m = movieService.createMovie(inMovie);
//               movieIds.add(m.id());
//               outMovies.add(m);
//            }
//        }

        movieSeriesRepository.save(new MovieSeries(id, inDto.title(), movieIds));
        return new MovieSeriesOutDto(id, inDto.title(), outMovies);
    }
//
//    public MovieSeriesOutDto update(String id, MovieSeriesUpdateDto updateDto) {
//
//        if (updateDto == null) {
//            return null;
//        }
//
//        MovieSeries toUpdate = movieSeriesRepository.findById(id).
//                orElseThrow(() -> new NoSuchElementException("Movie Series with id " + id + " does not exist"));
//
//        List<Movie> updateMovies = updateDto.movies();
//        List<String> updateIds = new ArrayList<>();
//        List<Movie> outMovies = new ArrayList<>();
//
//        for (Movie updateMovie : updateMovies) {
//            if (toUpdate.movieIds().contains(updateMovie.id())) {
//                movieService.changeMovie(updateMovie.id(), updateMovie);
//                outMovies.add(updateMovie);
//            } else {
//                outMovies.add(movieService.createMovie(updateMovie));
//                updateIds.add(outMovies.getLast().id());
//            }
//        }
//
//        MovieSeries updated = toUpdate
//                .withTitle(updateDto.title())
//                .withMovieIds(updateIds);
//
//        movieSeriesRepository.save(updated);
//        return new MovieSeriesOutDto(updated.id(), updated.title(), updateMovies);
//    }

    public boolean deleteById(String id){

        if (id == null) {
            return false;
        }

        movieSeriesRepository.deleteById(id);
        return !movieSeriesRepository.existsById(id);
    }
}