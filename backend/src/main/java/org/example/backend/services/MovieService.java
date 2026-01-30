package org.example.backend.services;

import org.example.backend.models.Movie;
import org.example.backend.dtos.MovieDto;
import org.example.backend.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    private final MovieRepository repo;

    public MovieService(MovieRepository repo) {
        this.repo = repo;
    }

    public String randomId() {
        return UUID.randomUUID().toString();
    }

    private boolean sameContent(List<String> a, List<String> b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return new HashSet<>(a).equals(new HashSet<>(b));
    }


    public List<Movie> getAllMovies() {
        return repo.findAll();
    }

    public Movie getMovieById(String id) {
        if(id == null || id.isEmpty()) return null;

        return repo.findById(id).orElse(null);
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

    public Movie createMovie(MovieDto movieToCreateDto) {
        Movie movie = new Movie(randomId(), movieToCreateDto.watchableID(), movieToCreateDto.streamable());
        return repo.save(movie);
    }

    public Movie changeMovie(String id, MovieDto newMovie) {
        Movie movie = repo.findById(id).orElse(null);

        if(movie == null) {
            return null;
        }

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
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
