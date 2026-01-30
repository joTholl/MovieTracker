package org.example.backend.controllers;

import lombok.AllArgsConstructor;
import org.example.backend.services.SeriesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/series")
@RestController
@AllArgsConstructor
public class SeriesController {

    private final SeriesService seriesService;
}
