package org.example.backend.services;

import org.example.backend.exceptions.WatchableNotFoundException;
import org.example.backend.DTOs.WatchableInDto;
import org.example.backend.models.Watchable;
import org.example.backend.repositories.WatchableRepository;
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

    WatchableRepository watchableRepository =  Mockito.mock(WatchableRepository.class);
    WatchableService watchableService = new WatchableService(watchableRepository);

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

        when(watchableRepository.findAll()).thenReturn(expected);

        // when
        List<Watchable> result = watchableService.getAll();

        // then
        assertEquals(1, result.size());
        assertEquals(w1, result.getFirst());
        assertEquals(expected, result);
        verify(watchableRepository, times(1)).findAll();
        verifyNoMoreInteractions(watchableRepository);
    }

    @Test
    void getById_throwsRuntimeException_whenIdIsNull() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> watchableService.getById(null));

        assertNotNull(ex);
        assertEquals("cannot find watchable with null id", ex.getMessage());
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

        when(watchableRepository.findById("1")).thenReturn(Optional.of(expected));

        // when
        Watchable result = watchableService.getById("1");

        // then
        assertEquals(expected, result);
        verify(watchableRepository, times(1)).findById("1");
        verifyNoMoreInteractions(watchableRepository);
    }

    @Test
    void create_throwsRuntimeException_whenInDtoIsNull() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> watchableService.create(null));

        assertNotNull(ex);
        assertEquals("cannot create Watchable with null InWatchableDto", ex.getMessage());
    }

    @Test
    void create_savesAndReturnsSavedWatchable() {
        // given
        WatchableInDto toCreate = new WatchableInDto(
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                "02:49",
                List.of("Christopher Nolan"),
                fakeDate,
                List.of("SciFi", "Drama"),
                0,
                12
        );

        Watchable saved = new Watchable(
                "1",
                toCreate.title(),
                toCreate.actors(),
                toCreate.duration(),
                toCreate.directors(),
                toCreate.releaseDate(),
                toCreate.genres(),
                toCreate.episode(),
                toCreate.ageRating());

        Mockito.when(watchableRepository.save(any())).thenReturn(saved);

        // when

        Watchable result = watchableService.create(toCreate);

        // then
        assertNotNull(result);
        assertEquals(result.directors(), saved.directors());
        verify(watchableRepository, times(1)).save(any());
        verifyNoMoreInteractions(watchableRepository);
    }

    @Test
    void deleteById_whenIdNull_throwsRuntimeException() {

        // when - then
        assertThrows(RuntimeException.class, () -> watchableService.deleteById(null));
        verify(watchableRepository, never()).deleteById(anyString());
        verifyNoMoreInteractions(watchableRepository);
    }

    @Test
    void deleteById_whenExists_deletes() {
        // given
        when(watchableRepository.existsById("1")).thenReturn(true);

        // when
        watchableService.deleteById("1");

        // then
        verify(watchableRepository, times(1)).existsById("1");
        verify(watchableRepository, times(1)).deleteById("1");
        verifyNoMoreInteractions(watchableRepository);
    }

    @Test
    void deleteById_whenMissing_throwsWatchableNotFoundException() {
        // given
        when(watchableRepository.existsById("1")).thenReturn(false);

        // when - then
        assertThrows(WatchableNotFoundException.class, () -> watchableService.deleteById("1"));
        verify(watchableRepository, times(1)).existsById("1");
        verify(watchableRepository, never()).deleteById(anyString());
        verifyNoMoreInteractions(watchableRepository);
    }



    @Test
    void update_whenExists_overwritesExisting() {
        // given
        String pathId = "1";

        WatchableInDto toUpdate = new WatchableInDto(
                "Odysseus",
                List.of("Matt Damon"),
                "02:49",
                List.of("Christopher Nolan"),
                fakeDate,
                List.of("Fantasy", "Historical"),
                0,
                12
        );

        Watchable existing = new Watchable(
                pathId,
                toUpdate.title(),
                toUpdate.actors(),
                toUpdate.duration(),
                toUpdate.directors(),
                toUpdate.releaseDate(),
                toUpdate.genres(),
                toUpdate.episode(),
                toUpdate.ageRating());

        when(watchableRepository.existsById(pathId)).thenReturn(true);
        when(watchableRepository.findById(pathId)).thenReturn(Optional.of(existing));
        when(watchableRepository.save(any())).thenReturn(existing);

        // when
        Watchable expected = watchableService.update(pathId, toUpdate);

        // then
        assertNotNull(expected);
        assertEquals(pathId, expected.id());
        assertEquals(toUpdate.title(), expected.title());

        verify(watchableRepository, times(1)).existsById(pathId);
        verify(watchableRepository, times(1)).findById(pathId);
        verify(watchableRepository, times(1)).save(existing);
        verifyNoMoreInteractions(watchableRepository);
    }
}