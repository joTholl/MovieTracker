package org.example.backend.controllers;

import org.example.backend.exceptions.WatchableNotFoundException;
import org.example.backend.models.Watchable;
import org.example.backend.repositories.WatchableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.mongodb.assertions.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // must-have for integration testing
@AutoConfigureMockMvc // must-have for integration testing
class WatchableControllerTest {

    @Autowired // must-have for integration testing
    private MockMvc mockMvc;

    @Autowired
    private WatchableRepository watchableRepository;

    @BeforeEach
    void cleanDb() {
        watchableRepository.deleteAll(); // ensures every test starts from empty DB
    }

    private final LocalDate fakeDate = LocalDate.of(2014, 6, 15);

    @Test
    void getAll() throws Exception {

        LocalDateTime releaseDate = LocalDateTime.of(2018, Month.NOVEMBER, 24, 0, 0, 0);

        Watchable w1 = new Watchable(
                "1",
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                fakeDate,
                List.of("SciFi", "Drama"),
                0,
                12
        );

        watchableRepository.save(w1);

        ResultMatcher jsonMatch = MockMvcResultMatchers.content().json(
                                """
                                [
                                  {
                                    "id": "1",
                                    "title": "Interstellar",
                                    "actors": ["Matthew McConaughey", "Anne Hathaway"],
                                    "duration": "02:49",
                                    "directors": ["Christopher Nolan"],
                                    "releaseDate": "%s",
                                    "genres": ["SciFi", "Drama"],
                                    "episode": 0,
                                    "ageRating": 12
                                  }
                                ]
                                """.formatted(fakeDate));

        // when + then
        mockMvc.perform(get("/api/watchables"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatch);
    }

    @Test
    void getById_whenFound_returns200AndJsonObject() throws Exception {

        // given
        LocalDateTime releaseDate = LocalDateTime.of(2018, Month.NOVEMBER, 24, 0, 0, 0);

        Watchable w1 = new Watchable(
                "1",
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                fakeDate,
                List.of("SciFi", "Drama"),
                0,
                12
        );

        watchableRepository.save(w1);

        ResultMatcher jsonMatch = MockMvcResultMatchers.content().json("""
                {
                      "id": "1",
                      "title": "Interstellar",
                      "actors": ["Matthew McConaughey", "Anne Hathaway"],
                      "duration": "02:49",
                      "directors": ["Christopher Nolan"],
                      "releaseDate": "%s",
                      "genres": ["SciFi", "Drama"],
                      "episode": 0,
                      "ageRating": 12
                }
                """.formatted(fakeDate));

        // when + then
        mockMvc.perform(get("/api/watchables/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatch);

    }

    @Test
    void create_returnsCreatedWatchable() throws Exception {

        // when + then
        mockMvc.perform(post("/api/watchables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "title": "Interstellar",
                                      "actors": ["Matthew McConaughey", "Anne Hathaway"],
                                      "duration": "02:49",
                                      "directors": ["Christopher Nolan"],
                                      "releaseDate": "%s",
                                      "genres": ["SciFi", "Drama"],
                                      "episode": 0,
                                      "ageRating": 12
                                    }
                                """.formatted(fakeDate)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Interstellar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value("02:49"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.directors").value("Christopher Nolan"));
    }

    @Test
    void deleteById() throws Exception {
        // when + then

        Watchable w1 = new Watchable(
                "1",
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                fakeDate,
                List.of("SciFi", "Drama"),
                0,
                12
        );

        watchableRepository.save(w1);
        mockMvc.perform(delete("/api/watchables/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteById_whenMissing_returnsWatchableNotFoundException() throws Exception {

        // when
        MvcResult result = mockMvc.perform(delete("/api/watchables/1"))
                .andExpect(status().isNotFound())
                .andReturn();

        Exception ex = result.getResolvedException();
        assertNotNull(ex);
        assertInstanceOf(WatchableNotFoundException.class, ex);

        assertTrue(ex.getMessage().contains("1"));
    }

    @Test
    void update() throws Exception {

        Watchable w1 = new Watchable(
                "1",
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                fakeDate,
                List.of("SciFi", "Drama"),
                0,
                12
        );

        watchableRepository.save(w1);

        MvcResult result = mockMvc.perform(put("/api/watchables/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "title": "Interstellar",
                          "actors": ["Matthew McConaughey", "Anne Hathaway"],
                          "duration": "02:49",
                          "directors": ["Christopher Nolan"],
                          "releaseDate": "%s",
                          "genres": ["SciFi", "Drama"],
                          "episode": 0,
                          "ageRating": 12
                        }
                        """.formatted(fakeDate)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Interstellar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value("02:49"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate").value(fakeDate.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.episode").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ageRating").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actors", org.hamcrest.Matchers.contains("Matthew McConaughey", "Anne Hathaway")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.directors", org.hamcrest.Matchers.contains("Christopher Nolan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genres", org.hamcrest.Matchers.contains("SciFi", "Drama")))
                .andReturn();

        Exception ex = result.getResolvedException();
        assertNull(ex);
    }

    @Test
    void getAllByFilters() throws Exception{

        Watchable inception = new Watchable(
                "2010-inc",
                "Inception",
                List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
                "2h 28m",
                List.of("Christopher Nolan"),
                LocalDate.of(2010, 7, 16),
                List.of("Sci-Fi", "Action", "Thriller"),
                0,
                12
        );

        Watchable shutterIsland = new Watchable(
                "2010-shi",
                "Shutter Island",
                List.of("Leonardo DiCaprio", "Mark Ruffalo", "Ben Kingsley"),
                "2h 18m",
                List.of("Martin Scorsese"),
                LocalDate.of(2010, 2, 19),
                List.of("Thriller", "Mystery", "Drama"),
                0,
                16
        );

        Watchable theSocialNetwork = new Watchable(
                "2010-tsn",
                "The Social Network",
                List.of("Leonardo DiCaprio", "Andrew Garfield", "Justin Timberlake"),
                "2h 0m",
                List.of("David Fincher"),
                LocalDate.of(2010, 10, 1),
                List.of("Drama", "Biography"),
                0,
                12
        );

        watchableRepository.save(inception);
        watchableRepository.save(shutterIsland);
        watchableRepository.save(theSocialNetwork);

        ResultMatcher jsonMatch = MockMvcResultMatchers.content().json(
                """
                [
                  {
                    "id": "2010-tsn",
                    "title": "The Social Network",
                    "actors": ["Leonardo DiCaprio", "Andrew Garfield", "Justin Timberlake"],
                    "duration": "2h 0m",
                    "directors": ["David Fincher"],
                    "releaseDate": "2010-10-01",
                    "genres": ["Drama", "Biography"],
                    "episode": 0,
                    "ageRating": 12
                  }
                ]
                """);

        MvcResult result = mockMvc.perform(get("/api/watchables/filtered")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                          [
                            {
                              "searchFilter": "ACTORS",
                              "input":"Leonardo DiCaprio"
                            },
                            {
                              "searchFilter": "GENRES",
                              "input": "Biography"
                            }
                          ]
                        """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatch)
                .andReturn();


        Exception ex = result.getResolvedException();
        assertNull(ex);
    }
}
