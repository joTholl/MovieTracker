package org.example.backend.services;

import lombok.AllArgsConstructor;
import org.example.backend.helpers.UtilityFunctions;
import org.example.backend.repositories.SeriesRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final UtilityFunctions utilityFunctions;

}
