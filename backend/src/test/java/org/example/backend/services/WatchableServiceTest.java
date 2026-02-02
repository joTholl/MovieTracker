package org.example.backend.services;

import org.example.backend.dtos.FilterDto;
import org.example.backend.exceptions.IdIsNullException;
import org.example.backend.exceptions.WatchableNotFoundException;
import org.example.backend.dtos.WatchableInDto;
import org.example.backend.models.Watchable;
import org.example.backend.repositories.WatchableRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.example.backend.enums.SearchFilter.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WatchableServiceTest {

//    @Mock
//    private WatchableRepo watchableRepo;
//
//    @InjectMocks
//    private WatchableService watchableService;

    WatchableRepository watchableRepository = Mockito.mock(WatchableRepository.class);
    WatchableService watchableService = new WatchableService(watchableRepository);

    private final LocalDate fakeDate = LocalDate.of(2014, 6, 15);

    @Test
    void getAll_ShouldReturnListOfSizeOne_ContainingGivenWatchable() {

        // GIVEN
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

        // WHEN
        List<Watchable> result = watchableService.getAll();

        // THEN
        assertEquals(1, result.size());
        assertEquals(w1, result.getFirst());
        assertEquals(expected, result);
        verify(watchableRepository, times(1)).findAll();
        verifyNoMoreInteractions(watchableRepository);
    }

    @Test
    void getById_throwsIdIsNullException_whenIdIsNull() {
        RuntimeException ex = assertThrows(IdIsNullException.class, () -> watchableService.getById(null));

        assertNotNull(ex);
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
    void create_throwsIllegalArgumentException_whenInDtoIsNull() {
        RuntimeException ex = assertThrows(IllegalArgumentException.class, () -> watchableService.create(null));

        assertNotNull(ex);
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
    void deleteById_whenIdIsNull_throwsIdIsNullException() {

        // when - then
        assertThrows(IdIsNullException.class, () -> watchableService.deleteById(null));
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
    void update_throwsIdIsNullException_whenIdIsNull() {
        RuntimeException ex = assertThrows(IdIsNullException.class, () -> watchableService.update(null, null));

        assertNotNull(ex);
    }

    @Test
    void update_throwsIllegalArgumentException_whenInDtoIsNull() {
        RuntimeException ex = assertThrows(IllegalArgumentException.class, () -> watchableService.update(null, null));

        assertNotNull(ex);
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

        verify(watchableRepository, times(1)).findById(pathId);
        verify(watchableRepository, times(1)).save(existing);
        verifyNoMoreInteractions(watchableRepository);
    }

    @Test
    void getAllByTitle() {
        Watchable inception = new Watchable(
                "2010-inc",
                "Inception",
                List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
                "2h 28m",
                List.of("Christopher Nolan"),
                LocalDate.of(2010, 7, 16),
                List.of("Sci-Fi", "Action", "Thriller"),
                0,
                12
        );

        Watchable shutterIsland = new Watchable(
                "2010-shi",
                "Shutter Island",
                List.of("Leonardo DiCaprio", "Mark Ruffalo", "Ben Kingsley"),
                "2h 18m",
                List.of("Martin Scorsese"),
                LocalDate.of(2010, 2, 19),
                List.of("Thriller", "Mystery", "Drama"),
                0,
                16
        );

        Watchable theSocialNetwork = new Watchable(
                "2010-tsn",
                "The Social Network",
                List.of("Jesse Eisenberg", "Andrew Garfield", "Justin Timberlake"),
                "2h 0m",
                List.of("David Fincher"),
                LocalDate.of(2010, 10, 1),
                List.of("Drama", "Biography"),
                0,
                12
        );

        List<Watchable> expected = List.of(inception, shutterIsland, theSocialNetwork);

        when(watchableRepository.findAll()).thenReturn(expected);

        // WHEN
        List<Watchable> result = watchableService.getAllByTitle(expected, "Inception");

        // THEN
        assertEquals(1, result.size());
        assertEquals(expected.getFirst(), result.getFirst());
        assertNotEquals(expected, result);
        verify(watchableRepository, times(1)).findAll();
        verifyNoMoreInteractions(watchableRepository);
    }

    @Test
    void getAllByActor_shouldReturnGivenList_whenSearchingForActor() {
        // GIVEN
        Watchable inception = new Watchable(
                "inc-2010",
                "Inception",
                List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
                "2h 28m",
                List.of("Christopher Nolan"),
                LocalDate.of(2010, 7, 16),
                List.of("Sci-Fi", "Action", "Thriller"),
                0,
                12
        );

        Watchable titanic = new Watchable(
                "tit-1997",
                "Titanic",
                List.of("Leonardo DiCaprio", "Kate Winslet", "Billy Zane"),
                "3h 15m",
                List.of("James Cameron"),
                LocalDate.of(1997, 12, 19),
                List.of("Drama", "Romance"),
                0,
                12
        );

        Watchable shutterIsland = new Watchable(
                "shi-2010",
                "Shutter Island",
                List.of("Leonardo DiCaprio", "Mark Ruffalo", "Ben Kingsley"),
                "2h 18m",
                List.of("Martin Scorsese"),
                LocalDate.of(2010, 2, 19),
                List.of("Thriller", "Mystery", "Drama"),
                0,
                16
        );

        List<Watchable> expected = List.of(inception, titanic, shutterIsland);

        when(watchableRepository.findAll()).thenReturn(expected);

        // WHEN
        List<Watchable> result = watchableService.getAllByActor(expected, "Leonardo DiCaprio");

        // THEN
        assertEquals(3, result.size());
        assertEquals(inception, result.getFirst());
        assertEquals(expected, result);
        verify(watchableRepository, times(1)).findAll();
        verifyNoMoreInteractions(watchableRepository);
    }

    @Test
    void getAllByDirectors() {
        Watchable inception = new Watchable(
                "nolan-inc-2010",
                "Inception",
                List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
                "2h 28m",
                List.of("Christopher Nolan"),
                LocalDate.of(2010, 7, 16),
                List.of("Sci-Fi", "Action", "Thriller"),
                0,
                12
        );

        Watchable interstellar = new Watchable(
                "nolan-int-2014",
                "Interstellar",
                List.of("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"),
                "2h 49m",
                List.of("Christopher Nolan"),
                LocalDate.of(2014, 11, 7),
                List.of("Sci-Fi", "Drama", "Adventure"),
                0,
                12
        );

        Watchable shutterIsland = new Watchable(
                "shi-2010",
                "Shutter Island",
                List.of("Leonardo DiCaprio", "Mark Ruffalo", "Ben Kingsley"),
                "2h 18m",
                List.of("Martin Scorsese"),
                LocalDate.of(2010, 2, 19),
                List.of("Thriller", "Mystery", "Drama"),
                0,
                16
        );

        List<Watchable> expected = List.of(inception, interstellar, shutterIsland);

        when(watchableRepository.findAll()).thenReturn(expected);

        // WHEN
        List<Watchable> result = watchableService.getAllByDirector(expected, "Christopher Nolan");

        // THEN
        assertEquals(2, result.size());
        assertEquals(inception, result.getFirst());
        assertNotEquals(expected, result);
        verify(watchableRepository, times(1)).findAll();
        verifyNoMoreInteractions(watchableRepository);
    }

    @Test
    void getAllByGenre() {
        Watchable inception = new Watchable(
                "scifi-inc-2010",
                "Inception",
                List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
                "2h 28m",
                List.of("Christopher Nolan"),
                LocalDate.of(2010, 7, 16),
                List.of("Sci-Fi", "Action", "Thriller"),
                0,
                12
        );

        Watchable theMatrix = new Watchable(
                "scifi-mtx-1999",
                "The Matrix",
                List.of("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                "2h 16m",
                List.of("Lana Wachowski", "Lilly Wachowski"),
                LocalDate.of(1999, 3, 31),
                List.of("Sci-Fi", "Action"),
                0,
                16
        );

        Watchable shutterIsland = new Watchable(
                "shi-2010",
                "Shutter Island",
                List.of("Leonardo DiCaprio", "Mark Ruffalo", "Ben Kingsley"),
                "2h 18m",
                List.of("Martin Scorsese"),
                LocalDate.of(2010, 2, 19),
                List.of("Thriller", "Mystery", "Drama"),
                0,
                16
        );

        List<Watchable> expected = List.of(inception, theMatrix, shutterIsland);

        when(watchableRepository.findAll()).thenReturn(expected);

        // WHEN
        List<Watchable> result = watchableService.getAllByGenre(expected,"Sci-Fi");

        // THEN
        assertEquals(2, result.size());
        assertEquals(inception, result.getFirst());
        assertNotEquals(expected, result);
        verify(watchableRepository, times(1)).findAll();
        verifyNoMoreInteractions(watchableRepository);
    }

    @Test
    void getAllByReleaseYear() {
        Watchable inception = new Watchable(
                "2010-inc",
                "Inception",
                List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
                "2h 28m",
                List.of("Christopher Nolan"),
                LocalDate.of(2010, 7, 16),
                List.of("Sci-Fi", "Action", "Thriller"),
                0,
                12
        );

        Watchable shutterIsland = new Watchable(
                "2010-shi",
                "Shutter Island",
                List.of("Leonardo DiCaprio", "Mark Ruffalo", "Ben Kingsley"),
                "2h 18m",
                List.of("Martin Scorsese"),
                LocalDate.of(2010, 2, 19),
                List.of("Thriller", "Mystery", "Drama"),
                0,
                16
        );

        Watchable theSocialNetwork = new Watchable(
                "2010-tsn",
                "The Social Network",
                List.of("Jesse Eisenberg", "Andrew Garfield", "Justin Timberlake"),
                "2h 0m",
                List.of("David Fincher"),
                LocalDate.of(2010, 10, 1),
                List.of("Drama", "Biography"),
                0,
                12
        );

        List<Watchable> expected = List.of(inception, shutterIsland, theSocialNetwork);

        when(watchableRepository.findAll()).thenReturn(expected);

        // WHEN
        List<Watchable> result = watchableService.getAllByReleaseYear(2010);

        // THEN
        assertEquals(3, result.size());
        assertEquals(inception, result.getFirst());
        assertEquals(expected, result);
        verify(watchableRepository, times(1)).findAll();
        verifyNoMoreInteractions(watchableRepository);
    }


    @Test
    void getAllBySingleFilter_shouldThrowException_whenFilterDtoIsNull() throws Exception {

        Exception ex = assertThrows(InvalidParameterException.class, () -> watchableService.getAllBySingleFilter(null, null));
        assertNotNull(ex);
    }

    @Test
    void getAllBySingleFilter_shouldPass_whenGivenFilterIsApplied() {

        //GIVEN
        Watchable inception = new Watchable(
                "2010-inc",
                "Inception",
                List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
                "2h 28m",
                List.of("Christopher Nolan"),
                LocalDate.of(2010, 7, 16),
                List.of("Sci-Fi", "Action", "Thriller"),
                0,
                12
        );

        Watchable shutterIsland = new Watchable(
                "2010-shi",
                "Shutter Island",
                List.of("Leonardo DiCaprio", "Mark Ruffalo", "Ben Kingsley"),
                "2h 18m",
                List.of("Martin Scorsese"),
                LocalDate.of(2010, 2, 19),
                List.of("Thriller", "Mystery", "Drama"),
                0,
                16
        );

        Watchable theSocialNetwork = new Watchable(
                "2010-tsn",
                "The Social Network",
                List.of("Jesse Eisenberg", "Andrew Garfield", "Justin Timberlake"),
                "2h 0m",
                List.of("David Fincher"),
                LocalDate.of(2010, 10, 1),
                List.of("Drama", "Biography"),
                0,
                12
        );

        //THEN
        List<Watchable> expected = List.of(inception, shutterIsland, theSocialNetwork);
        List<Watchable> result = watchableService.getAllBySingleFilter(expected, new FilterDto(ACTORS, "Leonardo DiCaprio"));

        assertEquals(2, result.size());
        assertEquals(result.getFirst(), inception);
        assertEquals(result.get(1), shutterIsland);
    }

    @Test
    void getAllByMultipleFilter_shouldThrowException_whenFilterDtoIsNull() throws Exception {
        Exception ex = assertThrows(InvalidParameterException.class, () -> watchableService.getAllByMultipleFilters(null));
        assertNotNull(ex);
    }

    @Test
    void getAllByMultiFilter_shouldPass_whenGivenFilterAreApplied() {
        //GIVEN
        Watchable inception = new Watchable(
                "2010-inc",
                "Inception",
                List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"),
                "2h 28m",
                List.of("Christopher Nolan"),
                LocalDate.of(2010, 7, 16),
                List.of("Sci-Fi", "Action", "Thriller"),
                0,
                12
        );

        Watchable shutterIsland = new Watchable(
                "2010-shi",
                "Shutter Island",
                List.of("Leonardo DiCaprio", "Mark Ruffalo", "Ben Kingsley"),
                "2h 18m",
                List.of("Martin Scorsese"),
                LocalDate.of(2010, 2, 19),
                List.of("Thriller", "Mystery", "Drama"),
                0,
                16
        );

        Watchable theSocialNetwork = new Watchable(
                "2010-tsn",
                "The Social Network",
                List.of("Leonardo DiCaprio", "Andrew Garfield", "Justin Timberlake"),
                "2h 0m",
                List.of("David Fincher"),
                LocalDate.of(2010, 10, 1),
                List.of("Drama", "Biography"),
                0,
                12
        );

        List<Watchable> given = List.of(inception, shutterIsland, theSocialNetwork);
        when(watchableRepository.findAll()).thenReturn(given);

        FilterDto filter1 = new FilterDto(ACTORS, "Leonardo DiCaprio");
        FilterDto filter2 = new FilterDto(GENRES, "Biography");
        List<FilterDto> filters = List.of(filter1, filter2);

        //THEN
        List<Watchable> expected = List.of(theSocialNetwork);
        List<Watchable> result = watchableService.getAllByMultipleFilters(filters);

        assertEquals(1, result.size());
        assertEquals(expected, result);
        assertEquals(result.getFirst(), theSocialNetwork);
        verify(watchableRepository, times(1)).findAll();
    }
}
