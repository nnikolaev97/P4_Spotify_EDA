package spotify.application;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spotify.domain.Playlist;
import spotify.domain.Track;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class SpotifyImplTreeMapTest {

    static Playlist playlist1 = new Playlist(1, "playlist 1", "Canciones Chingonas",
            List.of("Ole ole los caracoles", "Esto es un comentario"), LocalDate.of(2020, 1, 1));

    static Playlist playlist2 = new Playlist(2, "playlist 2", "Canciones Chingonas",
            List.of("Ole ole los caracoles", "Esto es un comentario"), LocalDate.of(2024, 1, 1));

    static Playlist playlist3 = new Playlist(3, "playlist 3", "Canciones Chingonas",
            List.of("Ole ole los caracoles", "Esto es un comentario"), LocalDate.of(2019, 1, 1));

    static Track track1 = new Track(1, "Bury the light", List.of("Casey Edwards", "Victor Borba"),
            List.of("Electronical", "Rock"), LocalDate.now(), "Bury_The_Light", 582);

    static Track track2 = new Track(2, "Devil Trigger", List.of("Casey Edwards", "Ali Edwards"),
            List.of("Electronical", "Rock"), LocalDate.now(), "Devil_Trigger", 400);

    static Track track3 = new Track(3, "The Vengeful One", List.of("Disturbed"),
            List.of("Hard Rock"), LocalDate.now(), "The_Vengeful_One", 252);

    static Track track4 = new Track(4, "Bury the light", List.of("Casey Edwards", "Victor Borba"),
            List.of("Electronical", "Rock"), LocalDate.now(), "Bury_The_Light", 582);

    static Track track5 = new Track(5, "Devil Trigger", List.of("Casey Edwards", "Ali Edwards"),
            List.of("Electronical", "Rock"), LocalDate.now(), "Devil_Trigger", 400);

    static Spotify spotify = new SpotifyImplTreeMap();
    static Map<Playlist, List<Track>> mapEmpty = new TreeMap<>();


    @BeforeAll
    static void setup(){

    }



    @Test
    void addPlaylist() {
        assertTrue(spotify.addPlaylist(playlist1));
        assertFalse(spotify.addPlaylist(playlist1));
    }

    @Test
    void addTrackToPlaylist() {

        IllegalArgumentException illegalArgumentExceptionNoPlaylist = assertThrows(IllegalArgumentException.class, () ->
                spotify.addTrackToPlaylist(playlist2, track1)
        );
        spotify.addPlaylist(playlist1);

        spotify.addTrackToPlaylist(playlist1, track1);


        IllegalArgumentException illegalArgumentExceptionAlreadyInPlaylist = assertThrows(IllegalArgumentException.class, () ->
                spotify.addTrackToPlaylist(playlist1, track1)
        );

        assertEquals("Playlist" + playlist2.getId() + " does not exist in Spotify.", illegalArgumentExceptionNoPlaylist.getMessage(), "Exception message should match");
        assertEquals("Song " + track1.getTitle() + " already exists in the list.", illegalArgumentExceptionAlreadyInPlaylist.getMessage(), "Exception message should match");


    }

    @Test
    void testAddTrackToPlaylist() {

        IllegalArgumentException illegalArgumentExceptionNoPlaylist = assertThrows(IllegalArgumentException.class, () ->
                spotify.addTrackToPlaylist(playlist2, 1, track1)
        );
        spotify.addPlaylist(playlist1);

        spotify.addTrackToPlaylist(playlist1, track1);



        IllegalArgumentException illegalArgumentExceptionAlreadyInPlaylist = assertThrows(IllegalArgumentException.class, () ->
                spotify.addTrackToPlaylist(playlist1, track1)
        );

        ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsExceptionLow = assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                spotify.addTrackToPlaylist(playlist1, -5, track2)
        );

        ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsExceptionHigh = assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                spotify.addTrackToPlaylist(playlist1, 100, track2)
        );

        assertEquals("Playlist" + playlist2.getId() + " does not exist in Spotify.", illegalArgumentExceptionNoPlaylist.getMessage(), "Exception message should match");
        assertEquals("Song " + track1.getTitle() + " already exists in the list.", illegalArgumentExceptionAlreadyInPlaylist.getMessage(), "Exception message should match");
        assertEquals("Index cannot have that value.", arrayIndexOutOfBoundsExceptionLow.getMessage(), "Exception message should match");
        assertEquals("Index cannot have that value.", arrayIndexOutOfBoundsExceptionHigh.getMessage(), "Exception message should match");


    }

    @Test
    void getTracks() {

        IllegalArgumentException illegalArgumentExceptionNoPlaylist = assertThrows(IllegalArgumentException.class, () ->
                spotify.addTrackToPlaylist(playlist2, 1, track1)
        );
        assertEquals("Playlist" + playlist2.getId() + " does not exist in Spotify.", illegalArgumentExceptionNoPlaylist.getMessage(), "Exception message should match");

        spotify.addPlaylist(playlist1);

        spotify.addTrackToPlaylist(playlist1, track1);



        assertFalse(spotify.getTracks(playlist1).isEmpty());


    }

    @Test
    void findByGenre() {

        assertTrue(spotify.findByGenre("Electronical").isEmpty());

        spotify.addPlaylist(playlist1);

        spotify.addTrackToPlaylist(playlist1, track1);


        assertTrue(spotify.findByGenre("WebiWabo").isEmpty());
        assertFalse(spotify.findByGenre("Rock").isEmpty());



    }

    @Test
    void findByArtist() {

        assertTrue(spotify.findByArtist("Casey Edwards").isEmpty());

        spotify.addPlaylist(playlist1);

        spotify.addTrackToPlaylist(playlist1, track1);


        assertTrue(spotify.findByArtist("WebiWabo").isEmpty());
        assertFalse(spotify.findByArtist("Victor Borba").isEmpty());



    }

    @Test
    void findLongestTrack() {

        IllegalArgumentException illegalArgumentExceptionNoPlaylist = assertThrows(IllegalArgumentException.class, () ->
                spotify.findLongestTrack(playlist1)
        );
        assertEquals("Playlist" + playlist1.getId() + " does not exist in Spotify.", illegalArgumentExceptionNoPlaylist.getMessage(), "Exception message should match");

        spotify.addPlaylist(playlist1);

        spotify.addTrackToPlaylist(playlist1, track1);
        spotify.addTrackToPlaylist(playlist1, track2);
        spotify.addTrackToPlaylist(playlist1, track3);



        assertEquals(spotify.findLongestTrack(playlist1), track1, "This is not the track with the longest duration");



    }

    @Test
    void findShortestTrack() {

        IllegalArgumentException illegalArgumentExceptionNoPlaylist = assertThrows(IllegalArgumentException.class, () ->
                spotify.findShortestTrack(playlist1)
        );
        assertEquals("Playlist" + playlist1.getId() + " does not exist in Spotify.", illegalArgumentExceptionNoPlaylist.getMessage(), "Exception message should match");

        spotify.addTrackToPlaylist(playlist1, track1);
        spotify.addTrackToPlaylist(playlist1, track2);
        spotify.addTrackToPlaylist(playlist1, track3);


        assertEquals(spotify.findShortestTrack(playlist1), track3, "This is not the track with the shortest duration");


    }

    @Test
    void getAverageDuration() {

        IllegalArgumentException illegalArgumentExceptionNoPlaylist = assertThrows(IllegalArgumentException.class, () ->
                spotify.getAverageDuration(playlist1)
        );
        assertEquals("Playlist" + playlist1.getId() + " does not exist in Spotify.", illegalArgumentExceptionNoPlaylist.getMessage(), "Exception message should match");

        spotify.addPlaylist(playlist1);

        spotify.addTrackToPlaylist(playlist1, track1);
        spotify.addTrackToPlaylist(playlist1, track2);
        spotify.addTrackToPlaylist(playlist1, track3);


        assertTrue(spotify.getAverageDuration(playlist1) > 0, "Average duration cannot be 0 or less");



    }

    @Test
    void getGenres() {

        IllegalArgumentException illegalArgumentExceptionNoPlaylist = assertThrows(IllegalArgumentException.class, () ->
                spotify.getGenres(playlist1)
        );
        assertEquals("Playlist" + playlist1.getId() + " does not exist in Spotify.", illegalArgumentExceptionNoPlaylist.getMessage(), "Exception message should match");

        spotify.addPlaylist(playlist1);

        spotify.addTrackToPlaylist(playlist1, track3);


        assertTrue(spotify.getGenres(playlist1).contains("Hard Rock"), "Genre should be in the list");



    }

    @Test
    void getSortedGenres() {

        IllegalArgumentException illegalArgumentExceptionNoPlaylist = assertThrows(IllegalArgumentException.class, () ->
                spotify.getSortedGenres(playlist1)
        );
        assertEquals("Playlist" + playlist1.getId() + " does not exist in Spotify.", illegalArgumentExceptionNoPlaylist.getMessage(), "Exception message should match");



        SortedSet<String> sortedGenres = spotify.getSortedGenres(playlist1);
        List<String> sortedGenresList = new ArrayList<>(sortedGenres);

        for (int i = 0; i < sortedGenresList.size() - 1; i++) {
            assertTrue(sortedGenresList.get(i).compareTo(sortedGenresList.get(i + 1)) <= 0,
                    "Genres should be sorted in ascending order.");
        }


    }

    @Test
    void getTopArtists() {

        IllegalArgumentException illegalArgumentExceptionNoPlaylist = assertThrows(IllegalArgumentException.class, () ->
                spotify.getTopArtists(playlist1)
        );
        assertEquals("Playlist" + playlist1.getId() + " does not exist in Spotify.", illegalArgumentExceptionNoPlaylist.getMessage(), "Exception message should match");


        assertTrue(spotify.getTopArtists(playlist1).size() <= 5, "Top Artists should always return the top 5");

        boolean containsCaseyEdwards = spotify.getTopArtists(playlist1).stream()
                .anyMatch(artist -> artist.artist().equals("Casey Edwards"));
        assertTrue(containsCaseyEdwards, "Artist should be in the top 5");

        boolean containsAliEdwards = spotify.getTopArtists(playlist1).stream()
                .anyMatch(artist -> artist.artist().equals("Ali Edwards"));
        assertFalse(containsAliEdwards, "Artist shouldn't be in the top 5");


    }

    @Test
    void findByDates() {

        assertTrue(spotify.findByDates(LocalDate.of(2024, 2, 23), LocalDate.of(2024, 2, 24)).isEmpty());

        IllegalArgumentException illegalArgumentExceptionWrongDates = assertThrows(IllegalArgumentException.class, () ->
                spotify.findByDates(LocalDate.of(2024, 2, 23), LocalDate.of(2024, 2, 22))
        );
        assertEquals("You cannot put an end date that is before the start date.", illegalArgumentExceptionWrongDates.getMessage(), "Exception message should match");


        spotify.addPlaylist(playlist1);
        spotify.addTrackToPlaylist(playlist1, track1);


        assertEquals(1, spotify.findByDates(LocalDate.of(2019, 4, 23), LocalDate.of(2026, 4, 27)).size());

        assertEquals(2, spotify.findByDates(LocalDate.of(2019, 4, 23), LocalDate.of(2026, 4, 27)).size());



    }

    @Test
    void findByDatesByTrackDate() {

        assertTrue(spotify.findByDatesByTrackDate(LocalDate.of(2024, 2, 23), LocalDate.of(2024, 2, 24), "Casey Edwards").isEmpty());

        /*IllegalArgumentException illegalArgumentExceptionWrongDates = assertThrows(IllegalArgumentException.class, () ->
                spotify.findByDatesByTrackDate(LocalDate.of(2024, 2, 23), LocalDate.of(2024, 2, 22), "Casey Akrokto")
        );
        assertEquals("You cannot put an end date that is before the start date.", illegalArgumentExceptionWrongDates.getMessage(), "Exception message should match");
        TEST PARA VER EL DIA
        */


        spotify.addPlaylist(playlist1);
        spotify.addTrackToPlaylist(playlist1, track1);


        assertEquals(1, spotify.findByDatesByTrackDate(LocalDate.of(2019, 4, 23), LocalDate.of(2026, 4, 27), "Casey Edwards").size());

        spotify.addPlaylist(playlist2);
        spotify.addTrackToPlaylist(playlist2, track2);


        assertEquals(1, spotify.findByDatesByTrackDate(LocalDate.of(2019, 4, 23), LocalDate.of(2026, 4, 27), "Ali Edwards").size());

        assertEquals(2, spotify.findByDatesByTrackDate(LocalDate.of(2019, 4, 23), LocalDate.of(2026, 4, 27), "Casey Edwards").size());



    }

    @Test
    void getAllPlaylist() {

        assertTrue(spotify.getAllPlaylist().isEmpty());

        spotify.addPlaylist(playlist1);

        assertTrue(spotify.getAllPlaylist().size() == 1);

        spotify.addPlaylist(playlist2);

        assertTrue(spotify.getAllPlaylist().size() == 2);


    }
}