package org.example.backend.controllers;

import org.example.backend.repositories.SeriesRepository;
import org.example.backend.repositories.SeasonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SeriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @BeforeEach
    void cleanDatabase() {
        seriesRepository.deleteAll();
        seasonRepository.deleteAll();
    }


    @Test
    void createSeries_shouldCreateSeries() throws Exception {
        String json = """
                {
                  "title": "Test Series",
                  "seasonInDtos": [],
                  "imageUrl": "img.png"
                }
                """;

        mockMvc.perform(post("/api/series")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Test Series"))
                .andExpect(jsonPath("$.seasons").isArray())
                .andExpect(jsonPath("$.seasons.length()").value(0));
    }

    @Test
    void getAllSeries_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/series"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllSeries_shouldReturnAllSeries() throws Exception {
        createSeries_shouldCreateSeries();

        mockMvc.perform(get("/api/series"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Series"));
    }

    @Test
    void getSeriesById_shouldReturnSeries() throws Exception {
        String response = mockMvc.perform(post("/api/series")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Test Series",
                                  "seasonInDtos": [],
                                  "imageUrl": "img.png"
                                }
                                """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = response.split("\"id\":\"")[1].split("\"")[0];

        mockMvc.perform(get("/api/series/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Test Series"));
    }

    @Test
    void getSeriesById_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(get("/api/series/doesNotExist"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateSeries_shouldUpdateSeries() throws Exception {
        String response = mockMvc.perform(post("/api/series")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Old Title",
                                  "seasonInDtos": [],
                                  "imageUrl": "old.png"
                                }
                                """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = response.split("\"id\":\"")[1].split("\"")[0];

        mockMvc.perform(put("/api/series/" + id)
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "New Title",
                                  "seasons": [],
                                  "imageUrl": "new.png"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.imageUrl").value("new.png"));
    }

    @Test
    void updateSeries_shouldReturn404_whenIdNotFound() throws Exception {
        mockMvc.perform(put("/api/series/doesNotExist")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "x",
                                  "seasons": [],
                                  "imageUrl": "x"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSeries_shouldDeleteSeries() throws Exception {
        String response = mockMvc.perform(post("/api/series")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "To Delete",
                                  "seasonInDtos": [],
                                  "imageUrl": "img.png"
                                }
                                """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = response.split("\"id\":\"")[1].split("\"")[0];

        mockMvc.perform(delete("/api/series/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/series/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSeries_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(delete("/api/series/doesNotExist"))
                .andExpect(status().isNotFound());
    }
}
