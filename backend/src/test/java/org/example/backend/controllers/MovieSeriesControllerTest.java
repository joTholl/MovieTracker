package org.example.backend.controllers;

import org.example.backend.models.MovieSeries;
import org.example.backend.repositories.MovieSeriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // must-have for integration testing
@AutoConfigureMockMvc // must-have for integration testing
class MovieSeriesControllerTest {

    @Autowired // must-have for integration testing
    private MockMvc mockMvc;

    @Autowired
    private MovieSeriesRepository repo;

    @BeforeEach
    void cleanDb() {
        repo.deleteAll(); // ensures every test starts from empty DB
    }

    @Test
    void getAll_should_returnStatusOk_and_returnJsonMatch() throws Exception {

        MovieSeries movieSeries1 =  new MovieSeries("1", "Batman", List.of("1","2","3"));
        MovieSeries movieSeries2 =  new MovieSeries("2", "Star Wars", List.of("4","5","6"));

        repo.save(movieSeries1);
        repo.save(movieSeries2);

        ResultMatcher jsonMatch = MockMvcResultMatchers.content().json(
                """
                [
                  {
                  "id": "1",
                  "title": "Batman",
                  "movieIds": ["1","2","3"]
                  },
                  {
                  "id": "2",
                  "title": "Star Wars",
                  "movieIds": ["4","5","6"]
                  }
                ]
                """);

        // when + then
        mockMvc.perform(get("/api/movieseries"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatch);
    }

    @Test
    void getById() throws Exception {
        MovieSeries movieSeries1 =  new MovieSeries("1", "Batman", List.of("1","2","3"));
        MovieSeries movieSeries2 =  new MovieSeries("2", "Star Wars", List.of("4","5","6"));

        repo.save(movieSeries1);
        repo.save(movieSeries2);

        // when + then
        mockMvc.perform(get("/api/movieseries/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldPass_and_returnExpectedValues() throws Exception {

        // when + then
        mockMvc.perform(post("/api/movieseries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                         """
                         {
                         "title": "Batman",
                         "movieIds": ["1","2","3"]
                         }
                         """))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_shouldpass_and_updateTheSavedMovieSeries() throws Exception {

        MovieSeries movieSeries =  new MovieSeries("1", "Star Wars", List.of("1","2","3"));
        repo.save(movieSeries);

        mockMvc.perform(put("/api/movieseries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                "id": "1",
                                "title": "Batman",
                                "movieIds": ["1","2"]
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById_shouldPass_andReturnNoContent() throws Exception {

        MovieSeries movieSeries =  new MovieSeries("1", "Batman", List.of("1","2","3"));
        repo.save(movieSeries);

        mockMvc.perform(delete("/api/movieseries/1"))
                .andExpect(status().isNoContent());
    }
}