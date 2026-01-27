package org.example.backend.controller;

import org.example.backend.models.Watchable;
import org.example.backend.repos.WatchableRepo;
import org.example.backend.services.WatchableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // must-have for integration testing
@AutoConfigureMockMvc // must-have for integration testing
class WatchableControllerTest {

    @Autowired // must-have for integration testing
    private MockMvc mockMvc;

    @Autowired
    private WatchableRepo watchableRepo;

    @BeforeEach
    void cleanDb() {
        watchableRepo.deleteAll(); // ensures every test starts from empty DB
    }

    @Test
    void getAll() throws Exception {

        LocalDateTime releaseDate = LocalDateTime.of(2018, Month.NOVEMBER, 24, 0, 0, 0);

        Watchable w1 = new Watchable(
                "1",
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                "2014, 11, 7, 0, 0",
                List.of("SciFi", "Drama"),
                0,
                12
        );

        watchableRepo.save(w1);

        ResultMatcher jsonMatch = MockMvcResultMatchers.content().json(        """
                                [
                                  {
                                    "id": "1",
                                    "title": "Interstellar",
                                    "actors": ["Matthew McConaughey", "Anne Hathaway"],
                                    "duration": "02:49",
                                    "directors": ["Christopher Nolan"],
                                    "releaseDate": "2014, 11, 7, 0, 0",
                                    "genres": ["SciFi", "Drama"],
                                    "episode": 0,
                                    "ageRating": 12
                                  }
                                ]
                                """);

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
                "2014, 11, 7, 0, 0",

                List.of("SciFi", "Drama"),
                0,
                12
        );

        watchableRepo.save(w1);

        ResultMatcher jsonMatch = MockMvcResultMatchers.content().json("""
                {
                      "id": "1",
                      "title": "Interstellar",
                      "actors": ["Matthew McConaughey", "Anne Hathaway"],
                      "duration": "02:49",
                      "directors": ["Christopher Nolan"],
                      "releaseDate": "2014, 11, 7, 0, 0",
                      //"releaseDate": "2018-11-24T00:00:00",
                      "genres": ["SciFi", "Drama"],
                      "episode": 0,
                      "ageRating": 12
                }
                """);

        // when + then
        mockMvc.perform(get("/api/watchables/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatch);

    }

//    @Test
//    void create_returns201AndCreatedWatchable() throws Exception {
//
//        // given: what the service returns after saving
//        LocalDateTime releaseDate = LocalDateTime.of(2018, Month.NOVEMBER, 24, 0, 0, 0);
//
//        Watchable newWatchable = new Watchable(
//                "1",
//                "Interstellar",
//                List.of("Matthew McConaughey", "Anne Hathaway"),
//                "02:49",
//                List.of("Christopher Nolan"),
//                releaseDate,
//                List.of("SciFi", "Drama"),
//                0,
//                12
//        );
//
//        WatchableService service = new WatchableService(watchableRepo);
//        service.create(newWatchable);
//
//        ResultMatcher jsonMatch = MockMvcResultMatchers.content().json("""
//                    {
//                      "id": "1",
//                      "title": "Interstellar",
//                      "actors": ["Matthew McConaughey", "Anne Hathaway"],
//                      "duration": "02:49",
//                      "directors": ["Christopher Nolan"],
//                      "releaseDate": "2018-11-24T00:00:00",
//                      "genres": ["SciFi", "Drama"],
//                      "episode": 0,
//                      "ageRating": 12
//                    }
//                """);
//
//        // when + then
//        mockMvc.perform(post("/api/watchables")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonMatch.toString()))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonMatch);
//    }
}
