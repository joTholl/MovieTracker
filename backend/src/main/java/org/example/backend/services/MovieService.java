package org.example.backend.services;

import org.example.backend.models.Movie;
import org.example.backend.models.MovieDto;
import org.example.backend.repositorys.MovieRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    private final MovieRepo repo;

    public MovieService(MovieRepo repo) {
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
        return repo.findById(id).get();
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
