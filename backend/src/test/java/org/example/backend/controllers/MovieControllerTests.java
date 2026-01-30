package org.example.backend.controllers;

import org.example.backend.models.Movie;
import org.example.backend.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository repo;

    @BeforeEach
    void cleanDb() {
        repo.deleteAll();
    }

    @Test
    void getAllMovies_shouldReturnListOfAllMovies() throws Exception {
        //GIVEN
        List<String> streamable = List.of("Amazon", "Prime");
        Movie movie = new Movie("1", "5", streamable);
        repo.save(movie);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movie"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                          [
                              {
                                  "id": "1",
                                  "watchableID": "5",
                                  "streamable": [
                                      "Amazon",
                                      "Prime"
                                  ]
                              }
                          ]
"""));
    }

    @Test
    void getMovieById_shouldReturnMovieById() throws Exception {
        List<String> streamable = List.of("Amazon", "Prime");
        Movie movie = new Movie("1", "5", streamable);
        repo.save(movie);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movie/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                              {
                                  "id": "1",
                                  "watchableID": "5",
                                  "streamable": [
                                      "Amazon",
                                      "Prime"
                                  ]
                              }
"""));
    }

    @Test
    void createMovie_ShouldReturnCreatedMovie() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "watchableID": "4",
                            "streamable": [
                                "Amazon",
                                "Prime",
                                "Disney+",
                                "Test"
                            ]
                        }
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                         """
                         {
                            "watchableID": "4",
                            "streamable": [
                                "Amazon",
                                "Prime",
                                "Disney+",
                                "Test"
                            ]
                        }
"""));
    }

    @Test
    void changeMovie_ShouldReturnChangedMovie() throws Exception {
        List<String> streamable = List.of("Amazon", "Prime");
        Movie movie = new Movie("4", "4", streamable);
        repo.save(movie);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/movie/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "watchableID": "4",
                                    "streamable": [
                                        "Amazon",
                                        "Prime",
                                        "Disney+",
                                        "Test123"
                                    ]
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        {
                            "id": "4",
                            "watchableID": "4",
                            "streamable": [
                                "Amazon",
                                "Prime",
                                "Disney+",
                                "Test123"
                            ]
                        }
     
              """));
    }

    @Test
    void deleteMovie_ShouldReturnTrueIfSuccessfull() throws Exception {
        List<String> streamable = List.of("Amazon", "Prime");
        Movie movie = new Movie("4", "4", streamable);
        repo.save(movie);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/movie/4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }


}
