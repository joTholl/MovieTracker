package org.example.backend.controllers;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.SeasonInDTO;
import org.example.backend.DTOs.SeasonWatchableIdDTO;
import org.example.backend.models.Season;
import org.example.backend.models.Watchable;
import org.example.backend.repositories.WatchableRepository;
import org.example.backend.repositories.SeasonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AllArgsConstructor
class SeasonControllerTest {
    @Autowired
    private final MockMvc mockMvc;
    @Autowired
    private final SeasonRepository seasonRepository;
    @Autowired
    private final WatchableRepository watchableRepository;

    private final Watchable watchable1 = new Watchable("abdhg12", "Inception", List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
            "2h 28m", List.of("Christopher Nolan"), LocalDate.of(2010, 7, 16), List.of("Sci-Fi", "Thriller", "Action"),
            0, 12);
    private final Watchable watchable2 = new Watchable("sghdjd3254", "Inception2", List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
            "2h 34m", List.of("Christopher Nolan"), LocalDate.of(2016, 7, 16), List.of("Sci-Fi", "Thriller", "Action"),
            0, 12);
    private final Season season1 = new Season("abc", 1, List.of(watchable1), List.of("www.something.com"));
    private final Season season2 = new Season("dfg", 2, List.of(watchable2), List.of("www.anything.com"));

    private final SeasonInDTO seasonInDTO1 = new SeasonInDTO(1, List.of(watchable1), List.of("www.something.com"));

    private final SeasonWatchableIdDTO swid1 = new SeasonWatchableIdDTO("abc", 1, List.of("abdhg12"), List.of("www.something.com"));
    private final SeasonWatchableIdDTO swid2 = new SeasonWatchableIdDTO("dfg", 2, List.of("sghdjd3254"), List.of("www.anything.com"));

    private final String season1Json = """
            {
              "id": "abc",
              "seasonNumber": 1,
              "watchables": [
                {
                  "id": "abdhg12",
                  "title": "Inception",
                  "actors": [
                    "Leonardo DiCaprio",
                    "Joseph Gordon-Levitt",
                    "Elliot Page"
                  ],
                  "duration": "2h 28m",
                  "directors": [
                    "Christopher Nolan"
                  ],
                  "releaseDate": "2010-07-16",
                  "genres": [
                    "Sci-Fi",
                    "Thriller",
                    "Action"
                  ],
                  "rating": 0,
                  "ageRating": 12
                }
              ],
              "urls": ["www.something.com"]
            }
            """;
    private final String bothSeasonsJson = """
            [{
              "id": "abc",
              "seasonNumber": 1,
              "watchables": [
                {
                  "id": "abdhg12",
                  "title": "Inception",
                  "actors": [
                    "Leonardo DiCaprio",
                    "Joseph Gordon-Levitt",
                    "Elliot Page"
                  ],
                  "duration": "2h 28m",
                  "directors": [
                    "Christopher Nolan"
                  ],
                  "releaseDate": "2010-07-16",
                  "genres": [
                    "Sci-Fi",
                    "Thriller",
                    "Action"
                  ],
                  "rating": 0,
                  "ageRating": 12
                }
              ],
              "urls": ["www.something.com"]
            },{
                      "id": "dfg",
                      "seasonNumber": 2,
                      "watchables": [
                        {
                          "id": "sghdjd3254",
                          "title": "Inception2",
                          "actors": [
                            "Leonardo DiCaprio",
                            "Joseph Gordon-Levitt",
                            "Elliot Page"
                          ],
                          "duration": "2h 34m",
                          "directors": [
                            "Christopher Nolan"
                          ],
                          "releaseDate": "2016-07-16",
                          "genres": [
                            "Sci-Fi",
                            "Thriller",
                            "Action"
                          ],
                          "rating": 0,
                          "ageRating": 12
                        }
                      ],
                      "urls": ["www.anything.com"]
                    }]""";

    @BeforeEach
    void cleanDb() {
        seasonRepository.deleteAll();
        seasonRepository.save(swid1);
        seasonRepository.save(swid2);
        watchableRepository.save(watchable1);
        watchableRepository.save(watchable2);
    }

    @Test
    void getAllSeasons_shouldReturnAllSeasons() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/seasons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(bothSeasonsJson));
    }

    @Test
    void getSeasonById_shouldReturnSeason() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/seasons/abc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(season1Json));
    }

    @Test
    void getSeasonById_shouldReturn404_whenCalledWithWrongId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/seasons/asgh356"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertInstanceOf(NoSuchElementException.class, result.getResolvedException()));
    }


}