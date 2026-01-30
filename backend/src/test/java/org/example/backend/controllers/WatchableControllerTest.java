package org.example.backend.controllers;

import org.example.backend.models.Watchable;
import org.example.backend.repositories.WatchableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

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

        ResultMatcher jsonMatch = MockMvcResultMatchers.content().json(        """
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
                        //.content(jsonMatch.toString()))
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
    void deleteById_whenMissing_returns404() throws Exception {
//        // given
//
//        WatchableService service = new WatchableService(watchableRepo);
//        ResponseEntity response = ResponseEntity.noContent().build();
//
//        // when + then
//        mockMvc.perform(delete("/api/watchables/1"))
//                .andExpect(status().isNoContent());
    }
}
