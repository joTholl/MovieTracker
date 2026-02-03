package org.example.backend.services;

import org.example.backend.exceptions.MovieNotFoundException;
import org.example.backend.models.Movie;
import org.example.backend.dtos.MovieInDto;
import org.example.backend.repositories.MovieRepository;
import org.example.backend.repositories.WatchableRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

        Movie movie = new Movie("1", "8", streamable, "abc");
        when(mockRepo.findById(movie.id())).thenReturn(Optional.of(movie));

        //WHEN
        Movie result = service.getMovieById("1");

        //THEN
        assertThat(result).isEqualTo(movie);
        verify(mockRepo).findById("1");
    }

    @Test
    void getMovieById_ShouldThrowExceptionIfNoId() {
        //GIVEN
        String emptyID = "";
        when(mockRepo.findById(emptyID)).thenReturn(Optional.empty());

        //THEN
        assertThatThrownBy(() -> service.getMovieById(emptyID))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessage("Movie mit ID  nicht gefunden");
    }

    @Test
    void createMovie_ShouldReturnNewMovie() {
        //GIVEN
        List<String> streamable = List.of("Amazon", "Prime");
        MovieInDto movie = new MovieInDto("8", streamable, "abc");
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
        Movie movie = new Movie("1", "8", streamable, "abc");

        when(mockRepo.existsById("1")).thenReturn(true);
        when(mockRepo.findById("1")).thenReturn(Optional.of(movie));

        WatchableService mockWatchableService = mock(WatchableService.class);
        when(mockWatchableService.deleteById("8")).thenReturn(true);

        MovieService serviceWithMockWatchable = new MovieService(mockRepo, mockWatchableService);

        doNothing().when(mockRepo).deleteById("1");

        //WHEN
        boolean result = serviceWithMockWatchable.deleteMovie(movie.id());

        //THEN
        assertTrue(result);

        verify(mockWatchableService).deleteById("8");
        verify(mockRepo).deleteById("1");
    }

    @Test
    void deleteMovie_ShouldThrowExceptionIfDeleteWasntSuccessfull() {
        //GIVEN
        String emptyID = "";

        //THEN
        assertThatThrownBy(() -> service.deleteMovie(emptyID))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessage("Movie mit ID  nicht gefunden");

    }

    @Test
    void changeMovie_ShouldUpdateMovie() {
        List<String> streamable = List.of("Amazon", "Prime");
        Movie movie = new Movie("1", "8", streamable, "abc");
        MovieInDto newMovie = new MovieInDto("9", streamable, "abc");
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

    @Test
    void changeMovie_ShouldThrowExceptionIfNoIdFound() {
        //GIVEN
        List<String> streamable = List.of("Amazon", "Prime");
        MovieInDto movie = new MovieInDto("1", streamable, "abc");
        String emptyID = "";

        //THEN
        assertThatThrownBy(() -> service.changeMovie(emptyID, movie))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessage("Movie mit ID  nicht gefunden");
    }



}


