package org.example.backend.services;

import org.example.backend.dtos.*;
import org.example.backend.helpers.UtilityFunctions;
import org.example.backend.models.Season;
import org.example.backend.models.Series;
import org.example.backend.repositories.SeriesRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeriesServiceTest {

    private final SeriesRepository seriesRepositoryMock = mock(SeriesRepository.class);
    private final SeasonService seasonServiceMock = mock(SeasonService.class);
    private final UtilityFunctions utilityFunctionsMock = mock(UtilityFunctions.class);

    private final SeriesService seriesService =
            new SeriesService(seriesRepositoryMock, seasonServiceMock, utilityFunctionsMock);

    private final Season season1 = new Season("s1", 1, List.of(), List.of("netflix"));
    private final Season season2 = new Season("s2", 2, List.of(), List.of("prime"));

    private final SeriesSeasonIdDto ssid1 =
            new SeriesSeasonIdDto("abc", "Test Series", List.of("s1"), "img.png");

    private final SeriesSeasonIdDto ssid2 =
            new SeriesSeasonIdDto("def", "Another Series", List.of("s2"), "img2.png");

    private final Series series1 =
            new Series("abc", "Test Series", List.of(season1), "img.png");

    private final Series series2 =
            new Series("def", "Another Series", List.of(season2), "img2.png");


    @Test
    void getAllSeries_shouldReturnAllSeries() {
        when(seriesRepositoryMock.findAll()).thenReturn(List.of(ssid1, ssid2));
        when(seasonServiceMock.getSeasonById("s1")).thenReturn(season1);
        when(seasonServiceMock.getSeasonById("s2")).thenReturn(season2);

        List<Series> result = seriesService.getAllSeries();

        verify(seriesRepositoryMock).findAll();
        verify(seasonServiceMock).getSeasonById("s1");
        verify(seasonServiceMock).getSeasonById("s2");

        assertEquals(List.of(series1, series2), result);
    }

    @Test
    void getSeriesById_shouldReturnSeries() {
        when(seriesRepositoryMock.findById("abc")).thenReturn(Optional.of(ssid1));
        when(seasonServiceMock.getSeasonById("s1")).thenReturn(season1);

        Series result = seriesService.getSeriesById("abc");

        verify(seriesRepositoryMock).findById("abc");
        verify(seasonServiceMock).getSeasonById("s1");

        assertEquals(series1, result);
    }

    @Test
    void getSeriesById_shouldThrowException_whenCalledWithFalseId() {
        when(seriesRepositoryMock.findById("x")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> seriesService.getSeriesById("x"));

        verify(seriesRepositoryMock).findById("x");
    }

    @Test
    void createSeries_shouldCreateSeries() {
        SeriesInDto inDto = new SeriesInDto(
                "Test Series",
                List.of(new SeasonInDto(1, List.of(), List.of("netflix"))),
                "img.png"
        );

        when(utilityFunctionsMock.createId()).thenReturn("abc");
        when(seasonServiceMock.createSeason(any())).thenReturn(season1);
        when(seriesRepositoryMock.save(any())).thenReturn(ssid1);

        Series result = seriesService.createSeries(inDto);

        verify(utilityFunctionsMock).createId();
        verify(seasonServiceMock).createSeason(any());
        verify(seriesRepositoryMock).save(any(SeriesSeasonIdDto.class));

        assertEquals(series1, result);
    }

    @Test
    void updateSeries_shouldUpdateTitleAndImage() {
        SeriesUpdateDto updateDto =
                new SeriesUpdateDto("abc","Updated", List.of(season1), "new.png");

        Series updated =
                new Series("abc", "Updated", List.of(season1), "new.png");

        when(seriesRepositoryMock.findById("abc")).thenReturn(Optional.of(ssid1));
        when(seasonServiceMock.updateSeason(eq("s1"), any())).thenReturn(season1);
        when(seriesRepositoryMock.save(any())).thenReturn(ssid1);

        Series result = seriesService.updateSeries("abc", updateDto);

        verify(seriesRepositoryMock).save(any());
        assertEquals(updated.title(), result.title());
        assertEquals(updated.thumbnail(), result.thumbnail());
    }

    @Test
    void updateSeries_shouldCreateSeason() {
        Season season3 = new Season("s3", 3, List.of(), List.of("disney"));

        SeriesUpdateDto updateDto =
                new SeriesUpdateDto(series1.id(), series1.title(), List.of(season1, season3), series1.thumbnail());

        when(seriesRepositoryMock.findById("abc")).thenReturn(Optional.of(ssid1));
        when(utilityFunctionsMock.createId()).thenReturn("s3");
        when(seasonServiceMock.createSeason(any())).thenReturn(season3);
        when(seriesRepositoryMock.save(any())).thenReturn(ssid1);

        Series result = seriesService.updateSeries("abc", updateDto);

        verify(seasonServiceMock).createSeason(any());
        assertEquals(2, result.seasons().size());
    }

    @Test
    void updateSeries_shouldDeleteSeason() {
        SeriesUpdateDto updateDto =
                new SeriesUpdateDto(series1.id(), series1.title(), new ArrayList<>(), series1.thumbnail());

        when(seriesRepositoryMock.findById("abc")).thenReturn(Optional.of(ssid1));
        when(seriesRepositoryMock.save(any())).thenReturn(ssid1);

        Series result = seriesService.updateSeries("abc", updateDto);

        verify(seasonServiceMock).deleteSeason("s1");
        assertTrue(result.seasons().isEmpty());
    }

    @Test
    void updateSeries_shouldThrowException_whenCalledWithFalseId() {
        when(seriesRepositoryMock.findById("x")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> seriesService.updateSeries("x", mock(SeriesUpdateDto.class)));
    }

    @Test
    void deleteSeries_shouldDeleteSeriesAndSeasons() {
        when(seriesRepositoryMock.findById("abc")).thenReturn(Optional.of(ssid1));
        when(seasonServiceMock.getSeasonById("s1")).thenReturn(season1);

        seriesService.deleteSeries("abc");

        verify(seasonServiceMock).deleteSeason("s1");
        verify(seriesRepositoryMock).deleteById("abc");
    }

    @Test
    void deleteSeries_shouldThrowException_whenCalledWithFalseId() {
        when(seriesRepositoryMock.findById("x")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> seriesService.deleteSeries("x"));
    }
}
