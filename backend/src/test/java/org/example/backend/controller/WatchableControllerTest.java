package org.example.backend.controller;

import org.example.backend.models.Watchable;
import org.example.backend.repos.WatchableRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
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
        String releaseDateString = releaseDate.toString();

        Watchable w1 = new Watchable(
                "1",
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                releaseDate,
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
                                    "releaseDate": "2018-11-24T00:00:00",
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
}
