package org.example.backend.controllers;

import org.example.backend.models.Movie;
import org.example.backend.models.Watchable;
import org.example.backend.repositories.MovieRepository;
import org.example.backend.repositories.WatchableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository repo;

    @Autowired
    private WatchableRepository repoWatchable;

    @BeforeEach
    void cleanDb() {
        repo.deleteAll();
    }

    @Test
    void getAllMovies_shouldReturnListOfAllMovies() throws Exception {
        //GIVEN
        List<String> streamable = List.of("Amazon", "Prime", "Disney+", "WoW");
        List<String> actors = List.of("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain");
        List<String> directors = List.of("Christopher Nolan");
        List<String> genres = List.of("SciFi", "Drama", "Adventure");
        LocalDate release = LocalDate.parse("2014-11-07");

        Watchable watchable = new Watchable("1", "Interstellar", actors, "02:49", directors, release , genres, 0, 12);
        Movie movie = new Movie("1", "1", streamable);

        repoWatchable.save(watchable);
        repo.save(movie);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movie"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(
                        """
                          [
                          {
                               "id": "1",
                               "watchable": {
                                   "id": "1",
                                   "title": "Interstellar",
                                   "actors": [
                                       "Matthew McConaughey",
                                       "Anne Hathaway",
                                       "Jessica Chastain"
                                   ],
                                   "duration": "02:49",
                                   "directors": [
                                       "Christopher Nolan"
                                   ],
                                   "releaseDate": "2014-11-07",
                                   "genres": [
                                       "SciFi",
                                       "Drama",
                                       "Adventure"
                                   ],
                                   "episode": 0,
                                   "ageRating": 12
                               },
                               "streamable": [
                                   "Amazon",
                                   "Prime",
                                   "Disney+",
                                   "WoW"
                               ]
                           }
                           ]
"""));
    }

    @Test
    void getMovieById_shouldReturnMovieById() throws Exception {
        //GIVEN
        List<String> streamable = List.of("Amazon", "Prime", "Disney+", "WoW");
        List<String> actors = List.of("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain");
        List<String> directors = List.of("Christopher Nolan");
        List<String> genres = List.of("SciFi", "Drama", "Adventure");
        LocalDate release = LocalDate.parse("2014-11-07");

        Watchable watchable = new Watchable("1", "Interstellar", actors, "02:49", directors, release , genres, 0, 12);
        Movie movie = new Movie("1", "1", streamable);

        repoWatchable.save(watchable);
        repo.save(movie);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movie/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(
                        """
                              {
                               "id": "1",
                               "watchable": {
                                   "id": "1",
                                   "title": "Interstellar",
                                   "actors": [
                                       "Matthew McConaughey",
                                       "Anne Hathaway",
                                       "Jessica Chastain"
                                   ],
                                   "duration": "02:49",
                                   "directors": [
                                       "Christopher Nolan"
                                   ],
                                   "releaseDate": "2014-11-07",
                                   "genres": [
                                       "SciFi",
                                       "Drama",
                                       "Adventure"
                                   ],
                                   "episode": 0,
                                   "ageRating": 12
                               },
                               "streamable": [
                                   "Amazon",
                                   "Prime",
                                   "Disney+",
                                   "WoW"
                               ]
                           }
"""));
    }

    @Test
    void getMovieById_ShouldReturn404IfMovieNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movie/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().string("Movie mit ID 1 nicht gefunden"));
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
                .andExpect(content().json(
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
                .andExpect(content().json(
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
    void changeMovie_ShouldReturn404IfMovieNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/movie/1")
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
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().string("Movie mit ID 1 nicht gefunden"));
    }

    @Test
    void deleteMovie_ShouldReturnTrueIfSuccessfull() throws Exception {
        List<String> streamable = List.of("Amazon", "Prime");
        Movie movie = new Movie("4", "4", streamable);
        repo.save(movie);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/movie/4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void deleteMovie_ShouldReturn404IfMovieNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/movie/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().string("Movie mit ID 1 nicht gefunden"));
    }


}
