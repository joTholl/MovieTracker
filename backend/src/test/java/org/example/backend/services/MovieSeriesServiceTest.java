package org.example.backend.services;

import org.example.backend.dtos.MovieSeriesInDto;
import org.example.backend.dtos.MovieSeriesOutDto;
import org.example.backend.dtos.MovieSeriesUpdateDto;
import org.example.backend.models.Movie;
import org.example.backend.models.MovieSeries;
import org.example.backend.repositories.MovieSeriesRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovieSeriesServiceTest {

    MovieSeriesRepository repo = Mockito.mock(MovieSeriesRepository.class);
    MovieService movieService = Mockito.mock(MovieService.class);
    MovieSeriesService movieSeriesService = new MovieSeriesService(repo, movieService);

    @Test
    void findAll_shouldPass_andReturnTheGivenList() {
        // GIVEN
        MovieSeries movieSeries1 =  new MovieSeries("1", "Batman", List.of("1","2","3"));
        MovieSeries movieSeries2 =  new MovieSeries("2", "Star Wars", List.of("4","5","6"));
        List<MovieSeries> expectedList = List.of(movieSeries1, movieSeries2);
        when(repo.findAll()).thenReturn(expectedList);

        // WHEN
        List<MovieSeries> actualList= movieSeriesService.findAll();

        // THEN
        assertEquals(2,actualList.size());
        assertEquals(expectedList,actualList);

        verify(repo).findAll();
    }

    @Test
    void getById_ShouldPass_andReturnTheGivenMovieSeries() {

        MovieSeries movieSeries =  new MovieSeries("2", "Star Wars", List.of("1","2","3"));
        when(repo.findById("2")).thenReturn(Optional.of(movieSeries));

        MovieSeriesOutDto actual = movieSeriesService.getById("2");

        assertEquals("2",actual.id());
        assertEquals(movieSeries.title(),actual.title());
        assertEquals(3, actual.movies().size());

        verify(repo).findById("2");
    }

    @Test
    void create() {

        MovieSeriesInDto movieSeriesInDto = new MovieSeriesInDto("Star Wars", List.of("1", "2", "3"));

        Movie movie1 = new Movie("1", "abc", List.of("AmazonPrime", "Netflix", "WoW"), "");
        Movie movie2 = new Movie("2", "def", List.of("Netflix", "Disney"), "");
        Movie movie3 = new Movie("3", "ghi", List.of("AmazonPrime", "Disney"), "");

        when(movieService.getMovieById("1")).thenReturn(movie1);
        when(movieService.getMovieById("2")).thenReturn(movie2);
        when(movieService.getMovieById("3")).thenReturn(movie3);

        MovieSeriesOutDto expected = new MovieSeriesOutDto("1", "Star Wars", List.of(movie1, movie2, movie3));
        MovieSeriesOutDto actual = movieSeriesService.create(movieSeriesInDto);

        assertNotNull(actual);
        assertNotNull(actual.id());
        assertEquals(expected.movies(),actual.movies());

        verify(movieService).getMovieById("1");
        verify(movieService).getMovieById("2");
        verify(movieService).getMovieById("3");
    }

    @Test
    void update() {

        MovieSeries savedMovieSeries = new MovieSeries("1", "Lord of the Rings", List.of("5", "6", "7"));
        MovieSeriesUpdateDto updateDto = new MovieSeriesUpdateDto("1","Star Wars", List.of("1", "2", "3"));

        Movie movie1 = new Movie("1", "abc", List.of("AmazonPrime", "Netflix", "WoW"), "");
        Movie movie2 = new Movie("2", "def", List.of("Netflix", "Disney"), "");
        Movie movie3 = new Movie("3", "ghi", List.of("AmazonPrime", "Disney"), "");

        when(repo.findById("1")).thenReturn(Optional.of(savedMovieSeries));

        when(movieService.getMovieById("1")).thenReturn(movie1);
        when(movieService.getMovieById("2")).thenReturn(movie2);
        when(movieService.getMovieById("3")).thenReturn(movie3);

        MovieSeriesOutDto expected = new MovieSeriesOutDto("1", "Star Wars", List.of(movie1, movie2, movie3));
        MovieSeriesOutDto actual = movieSeriesService.update("1", updateDto);

        assertNotNull(actual);
        assertNotNull(actual.id());
        assertEquals(expected.movies(),actual.movies());

        verify(repo).findById("1");
        verify(movieService).getMovieById("1");
        verify(movieService).getMovieById("2");
        verify(movieService).getMovieById("3");
        verify(repo).save(any());
    }

    @Test
    void delete_shouldPass_andDeleteTheGivenMovieSeries() {

        when(repo.existsById("2")).thenReturn(true);

        movieSeriesService.deleteById("2");

        verify(repo, times(2)).existsById("2");
        verify(repo).deleteById("2");
    }
}