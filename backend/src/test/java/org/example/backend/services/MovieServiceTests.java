package org.example.backend.services;

import org.example.backend.models.Movie;
import org.example.backend.DTOs.MovieDto;
import org.example.backend.repositories.MovieRepository;
import org.example.backend.repositories.WatchableRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MovieServiceTests {

    private final MovieRepository mockRepo = mock(MovieRepository.class);
    private final WatchableRepository mockRepoWatchable = mock(WatchableRepository.class);
    private final WatchableService watchableService = new WatchableService(mockRepoWatchable);
    private final MovieService service = new MovieService(mockRepo, watchableService);

    @Test
    void getMovieById_ShouldReturnSearchedMovie() {
        //GIVEN
        List<String> streamable = List.of("Amazon", "Prime");
        Movie movie = new Movie("1", "8", streamable);
        when(mockRepo.findById(movie.id())).thenReturn(Optional.of(movie));

        //WHEN
        Movie result = service.getMovieById("1");

        //THEN
        assertThat(result).isEqualTo(movie);
        verify(mockRepo).findById("1");
    }

    @Test
    void getMovieById_ShouldReturnNullIfNoId() {
        //GIVEN
        List<String> streamable = List.of("Amazon", "Prime");
        Movie movie = new Movie("1", "8", streamable);
        when(mockRepo.findById(movie.id())).thenReturn(Optional.of(movie));

        //WHEN
        Movie result = service.getMovieById("");

        //THEN
        assertThat(result).isEqualTo(null);
    }

    @Test
    void createMovie_ShouldReturnNewMovie() {
        //GIVEN
        List<String> streamable = List.of("Amazon", "Prime");
        MovieDto movie = new MovieDto("8", streamable);
        when(mockRepo.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //WHEN
        Movie result = service.createMovie(movie);

        //THEN
        assertThat(result).isNotNull();
        assertThat(result.id()).isNotNull();
        assertThat(result.watchableID()).isEqualTo(movie.watchableID());
        assertThat(result.streamable()).isEqualTo(movie.streamable());
        verify(mockRepo).save(any(Movie.class));
    }

    @Test
    void deleteMovie_ShouldReturnTrueIfDeletedSuccessfull() {
        //GIVEN
        List<String> streamable = List.of("Amazon", "Prime");
        Movie movie = new Movie("1", "8", streamable);
        when(mockRepo.existsById("1")).thenReturn(true);
        doNothing().when(mockRepo).deleteById("1");

        //WHEN
        boolean result = service.deleteMovie("1");

        //THEN
        assertTrue(result);

        verify(mockRepo).deleteById("1");
    }

    @Test
    void deleteMovie_ShouldReturnFalseIfDeletedSuccessfull() {
        //GIVEN
        List<String> streamable = List.of("Amazon", "Prime");
        Movie movie = new Movie("1", "8", streamable);
        when(mockRepo.existsById("2")).thenReturn(false);

        //WHEN
        boolean result = service.deleteMovie("2");

        //THEN
        assertFalse(result);
    }

    @Test
    void changeMovie_ShouldUpdateMovie() {
        List<String> streamable = List.of("Amazon", "Prime");
        Movie movie = new Movie("1", "8", streamable);
        MovieDto newMovie = new MovieDto("9", streamable);
        when(mockRepo.findById("1")).thenReturn(Optional.of(movie));
        when(mockRepo.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //WHEN

        Movie result = service.changeMovie("1", newMovie);

        //THEN
        assertThat(result).isNotNull();
        assertThat(result.watchableID()).isEqualTo("9");
        assertThat(result.streamable()).isEqualTo(streamable);

        verify(mockRepo).save(any(Movie.class));
    }



}


