package org.example.backend.services;

import org.example.backend.dtos.MovieOutDto;
import org.example.backend.exceptions.MovieNotFoundException;
import org.example.backend.models.Movie;
import org.example.backend.dtos.MovieInDto;
import org.example.backend.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    private final MovieRepository repo;
    private final WatchableService watchableService;

    public MovieService(MovieRepository repo, WatchableService watchableService) {
        this.repo = repo;
        this.watchableService = watchableService;
    }

    public String randomId() {
        return UUID.randomUUID().toString();
    }

    private boolean sameContent(List<String> a, List<String> b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return new HashSet<>(a).equals(new HashSet<>(b));
    }


    public List<MovieOutDto> getAllMovies() {
        List<Movie> allMovies = repo.findAll();
        List<MovieOutDto> returnList = new ArrayList<>();

        for(Movie movie : allMovies) {
            returnList.add(new MovieOutDto(movie, watchableService));
        }

        return returnList;
    }

    public Movie getMovieById(String id) {
        return repo.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    public MovieOutDto getMovieOutDtoById(String id) {
        Movie movie = getMovieById(id);
        return new MovieOutDto(movie, watchableService);
    }

    /** Auskommentiert weil WatchableRepo noch fehlt

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
     **/

    public Movie createMovie(MovieInDto movieToCreateDto) {
        Movie movie = new Movie(randomId(), movieToCreateDto.watchableID(), movieToCreateDto.streamable(), movieToCreateDto.thumbnail());
        return repo.save(movie);
    }

    public Movie changeMovie(String id, MovieInDto newMovie) {
        Movie movie = repo.findById(id).orElseThrow(() -> new MovieNotFoundException(id));

        String watchable = movie.watchableID();
        List<String> streamable = movie.streamable();

        if (newMovie.watchableID() != null && !newMovie.watchableID().equals(watchable)) {
            watchable = newMovie.watchableID();
        }

        if (newMovie.streamable() != null && !sameContent(streamable, newMovie.streamable())) {
            streamable = newMovie.streamable();
        }

        return repo.save(movie
                .withWatchableID(watchable)
                .withStreamable(streamable)
        );
    }
    public boolean deleteMovie(String id) {
        if(!repo.existsById(id)) {
            throw new MovieNotFoundException(id);
        }
        watchableService.deleteById(getMovieById(id).watchableID());
        repo.deleteById(id);
        return true;
    }
}
