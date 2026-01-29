package org.example.backend.services;

import org.example.backend.dtos.SeasonInDTO;
import org.example.backend.dtos.SeasonWatchableIdDTO;
import org.example.backend.helpers.UtilityFunctions;
import org.example.backend.dtos.WatchableInDto;
import org.example.backend.models.Season;
import org.example.backend.models.Watchable;
import org.example.backend.repositories.SeasonRepository;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeasonServiceTest {

    private final SeasonRepository seasonRepositoryMock = mock(SeasonRepository.class);
    private final WatchableService watchableServiceMock = mock(WatchableService.class);
    private  final UtilityFunctions utilityFunctionsMock = mock(UtilityFunctions.class);
    private final SeasonService seasonService = new SeasonService(seasonRepositoryMock, watchableServiceMock, utilityFunctionsMock);
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

    @Test
    void getAllSeasons_shouldReturnAllSeasons() {
        when(seasonRepositoryMock.findAll()).thenReturn(List.of(swid1, swid2));
        when(watchableServiceMock.getById("abdhg12")).thenReturn(watchable1);
        when(watchableServiceMock.getById("sghdjd3254")).thenReturn(watchable2);
        List<Season> seasons = seasonService.getAllSeasons();
        verify(seasonRepositoryMock).findAll();
        verify(watchableServiceMock).getById("abdhg12");
        verify(watchableServiceMock).getById("sghdjd3254");
        assertEquals(seasons, List.of(season1, season2));
    }

    @Test
    void getSeasonById_shouldReturnSeason() {
        when(seasonRepositoryMock.findById("abc")).thenReturn(Optional.of(swid1));
        when(watchableServiceMock.getById("abdhg12")).thenReturn(watchable1);
        Season season = seasonService.getSeasonById("abc");
        verify(seasonRepositoryMock).findById("abc");
        verify(watchableServiceMock).getById("abdhg12");
        assertEquals(season, season1);
    }

    @Test
    void getSeasonById_shouldThrowException_whenCalledWithFalseId() {
        when(seasonRepositoryMock.findById("sdh")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> seasonService.getSeasonById("sdh"));
        verify(seasonRepositoryMock).findById("sdh");
    }

    @Test
    void createSeason_shouldCreateSeason() {
        when(utilityFunctionsMock.createId()).thenReturn("abc");
        when(watchableServiceMock.create(new WatchableInDto(watchable1))).thenReturn(watchable1);
        when(seasonRepositoryMock.save(swid1)).thenReturn(swid1);
        when(seasonRepositoryMock.findById("abc")).thenReturn(Optional.of(swid1));
        when(watchableServiceMock.getById("abdhg12")).thenReturn(watchable1);
        Season season = seasonService.createSeason(seasonInDTO1);
        verify(seasonRepositoryMock).save(swid1);
        verify(seasonRepositoryMock).findById("abc");
        verify(watchableServiceMock).getById("abdhg12");
        assertEquals(season, season1);

    }

    @Test
    void updateSeason_shouldUpdateSeason() {
        when(utilityFunctionsMock.createId()).thenReturn("abc");
        Season season3 = season1.withSeasonNumber(2);
        SeasonWatchableIdDTO swid3 = swid1.withSeasonNumber(2);
        when(seasonRepositoryMock.save(swid3)).thenReturn(swid3);
        when(seasonRepositoryMock.findById("abc")).thenReturn(Optional.of(swid3));
        when(watchableServiceMock.getById("abdhg12")).thenReturn(watchable1);
        Season season = seasonService.updateSeason(season3.id(), seasonInDTO1.withSeasonNumber(2));
        verify(seasonRepositoryMock).save(swid3);
        verify(seasonRepositoryMock,times(2)).findById("abc");
        verify(watchableServiceMock).getById("abdhg12");
        assertEquals(season, season3);
    }

    @Test
    void updateSeason_shouldThrowException_whenCalledWithFalseId() {
        when(seasonRepositoryMock.findById("sdh")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> seasonService.updateSeason("sdh", seasonInDTO1));
        verify(seasonRepositoryMock).findById("sdh");
    }

    @Test
    void deleteSeason_shouldDeleteSeason() {
        when(seasonRepositoryMock.findById("abc")).thenReturn(Optional.of(swid1));
        when(watchableServiceMock.getById("abdhg12")).thenReturn(watchable1);
        doNothing().when(seasonRepositoryMock).deleteById(season1.id());
        seasonService.deleteSeason("abc");
        verify(watchableServiceMock).getById("abdhg12");
        verify(seasonRepositoryMock, times(2)).findById("abc");
        verify(seasonRepositoryMock).deleteById(season1.id());
    }

    @Test
    void deleteSeason_shouldThrowException_whenCalledWithFalseId() {
        when(seasonRepositoryMock.findById("sdh")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> seasonService.deleteSeason("sdh"));
        verify(seasonRepositoryMock).findById("sdh");
    }
}