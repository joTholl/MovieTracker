package org.example.backend.services;

import org.example.backend.models.Watchable;
import org.example.backend.repos.WatchableRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WatchableServiceTest {

//    @Mock
//    private WatchableRepo watchableRepo;
//
//    @InjectMocks
//    private WatchableService watchableService;

    WatchableRepo watchableRepo =  Mockito.mock(WatchableRepo.class);
    WatchableService watchableService = new WatchableService(watchableRepo);

    @Test
    void getAll_ShouldReturnListOfSizeOne_ContainingGivenWatchable() {

        // given
        Watchable w1 = new Watchable(
                "1",
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                //LocalDateTime.of(2014, 11, 7, 0, 0),
                "2014, 11, 7, 0, 0",

                List.of("SciFi", "Drama"),
                0,
                12
        );

        List<Watchable> expected = List.of(w1);

        when(watchableRepo.findAll()).thenReturn(expected);

        // when
        List<Watchable> result = watchableService.getAll();

        // then
        assertEquals(1, result.size());
        assertEquals(w1, result.get(0));
        assertEquals(expected, result);
        verify(watchableRepo, times(1)).findAll();
        verifyNoMoreInteractions(watchableRepo);
    }

    @Test
    void getById_whenFound_returnsWatchable() {
        // given
        Watchable expected = new Watchable(
                "1",
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                //LocalDateTime.of(2014, 11, 7, 0, 0),
                "2014, 11, 7, 0, 0",
                List.of("SciFi", "Drama"),
                0,
                12
        );

        when(watchableRepo.findById("1")).thenReturn(Optional.of(expected));

        // when
        Watchable result = watchableService.getById("1");

        // then
        assertEquals(expected, result);
        verify(watchableRepo, times(1)).findById("1");
        verifyNoMoreInteractions(watchableRepo);
    }

    @Test
    void create_savesAndReturnsSavedWatchable() {
        // given
        Watchable toCreate = new Watchable(
                null, // no id yet (Mongo typically generates it)
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                //LocalDateTime.of(2014, 11, 7, 0, 0),
                "2014, 11, 7, 0, 0",
                List.of("SciFi", "Drama"),
                0,
                12
        );

        Watchable saved = new Watchable(
                "1",
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                //LocalDateTime.of(2014, 11, 7, 0, 0),
                "2014, 11, 7, 0, 0",
                List.of("SciFi", "Drama"),
                0,
                12
        );

        when(watchableRepo.save(toCreate)).thenReturn(saved);

        // when
        Watchable result = watchableService.create(toCreate);

        // then
        assertEquals(saved, result);
        verify(watchableRepo, times(1)).save(toCreate);
        verifyNoMoreInteractions(watchableRepo);
    }
}