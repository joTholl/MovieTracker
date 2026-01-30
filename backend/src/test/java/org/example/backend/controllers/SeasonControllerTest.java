package org.example.backend.controllers;

import org.example.backend.dtos.SeasonWatchableIdDto;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
class SeasonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private WatchableRepository watchableRepository;

    private final Watchable watchable1 = new Watchable("abdhg12", "Inception", List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
            "2h 28m", List.of("Christopher Nolan"), LocalDate.of(2010, 7, 16), List.of("Sci-Fi", "Thriller", "Action"),
            0, 12);
    private final Watchable watchable2 = new Watchable("sghdjd3254", "Inception2", List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
            "2h 34m", List.of("Christopher Nolan"), LocalDate.of(2016, 7, 16), List.of("Sci-Fi", "Thriller", "Action"),
            0, 12);

    private final SeasonWatchableIdDto swid1 = new SeasonWatchableIdDto("abc", 1, List.of("abdhg12"), List.of("www.something.com"));
    private final SeasonWatchableIdDto swid2 = new SeasonWatchableIdDto("dfg", 2, List.of("sghdjd3254"), List.of("www.anything.com"));

    private final String seasonJson1 = """
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
                  "episode": 0,
                  "ageRating": 12
                }
              ],
              "streamables": ["www.something.com"]
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
                  "episode": 0,
                  "ageRating": 12
                }
              ],
              "streamables": ["www.something.com"]
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
                          "episode": 0,
                          "ageRating": 12
                        }
                      ],
                      "streamables": ["www.anything.com"]
                    }]""";
    private final String seasonInDTOJson = """
            {
            "seasonNumber": 1,
            "watchableInDtos": [
              {
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
                "episode": 0,
                "ageRating": 12
              }
            ],
            
            "streamables": ["www.something.com"]
            }""";
    private final String seasonUpdateDTOJson = """
            {
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
                "episode": 0,
                "ageRating": 12
              }
            ],
            
            "streamables": ["www.something.com"]
            }""";
    private final String seasonInDTOResultJSON = """
            {
            "seasonNumber": 1,
            "watchables": [
              {
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
                "episode": 0,
                "ageRating": 12
              }
            ],
            
            "streamables": ["www.something.com"]
            }""";

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
                .andExpect(MockMvcResultMatchers.content().json(seasonJson1));
    }

    @Test
    void getSeasonById_shouldReturn404_whenCalledWithWrongId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/seasons/asgh356"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertInstanceOf(NoSuchElementException.class, result.getResolvedException()));
    }

    @Test
    void createSeason_shouldCreateSeason() throws Exception {
        seasonRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/seasons").contentType(APPLICATION_JSON).content(seasonInDTOJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(seasonInDTOResultJSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    void updateSeason_shouldUpdateSeason() throws Exception {
        String seasonInDTOJson2 = seasonUpdateDTOJson.replace("Action", "Horror");
        String seasonJson2 = seasonJson1.replace("Action", "Horror");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/seasons/abc").contentType(APPLICATION_JSON).content(seasonInDTOJson2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(seasonJson2));
    }

    @Test
    void updateSeason_shouldReturn404_whenCalledWithWrongId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/seasons/asgh356").contentType(APPLICATION_JSON).content(seasonInDTOJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteSeasonById_shouldDeleteSeason() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/seasons/abc"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteSeasonById_shouldReturn404_whenCalledWithWrongId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/seasons/asgh356"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}