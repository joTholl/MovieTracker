package org.example.backend.services;

import org.example.backend.exceptions.WatchableNotFoundException;
import org.example.backend.models.Watchable;
import org.example.backend.repos.WatchableRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
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

    private final LocalDate fakeDate = LocalDate.of(2014, 6, 15);

    @Test
    void getAll_ShouldReturnListOfSizeOne_ContainingGivenWatchable() {

        // given
        Watchable w1 = new Watchable(
                "1",
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                fakeDate,
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
        assertEquals(w1, result.getFirst());
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
                fakeDate,
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
                fakeDate,
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
                fakeDate,
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

    @Test
    void deleteById_whenExists_deletes() {
        // given
        when(watchableRepo.existsById("1")).thenReturn(true);

        // when
        watchableService.deleteById("1");

        // then
        verify(watchableRepo, times(1)).existsById("1");
        verify(watchableRepo, times(1)).deleteById("1");
        verifyNoMoreInteractions(watchableRepo);
    }

    @Test
    void deleteById_whenMissing_throwsWatchableNotFoundException() {
        // given
        when(watchableRepo.existsById("1")).thenReturn(false);

        // when - then
        assertThrows(WatchableNotFoundException.class, () -> watchableService.deleteById("1"));
        verify(watchableRepo, times(1)).existsById("1");
        verify(watchableRepo, never()).deleteById(anyString());
        verifyNoMoreInteractions(watchableRepo);
    }

    @Test
    void update_whenExists_overwritesExisting() {
        // given
        String pathId = "1";

        Watchable existing = new Watchable(
                pathId,
                "Interstellar (Edited)",
                List.of("Matthew McConaughey"),
                "02:49",
                List.of("Christopher Nolan"),
                //LocalDateTime.of(2014, 11, 7, 0, 0),
                fakeDate,
                List.of("SciFi"),
                0,
                12
        );

        Watchable updated = new Watchable(
                pathId,
                "Odysseus",
                List.of("Matt Damon"),
                "02:49",
                List.of("Christopher Nolan"),
                //LocalDateTime.of(2014, 11, 7, 0, 0),
                fakeDate,
                List.of("Fantasy", "Historical"),
                0,
                12
        );

        when(watchableRepo.existsById(pathId)).thenReturn(true);
        when(watchableRepo.findById(pathId)).thenReturn(Optional.of(updated));
        when(watchableRepo.save(updated)).thenReturn(updated);

        // when
        Watchable expected = watchableService.update(pathId, updated);

        // then
        assertEquals(pathId, expected.id());
        assertEquals(updated.title(), expected.title());

        verify(watchableRepo, times(1)).existsById(pathId);
        verify(watchableRepo, times(1)).findById(pathId);
        verify(watchableRepo, times(1)).save(updated);
        verifyNoMoreInteractions(watchableRepo);
    }
}