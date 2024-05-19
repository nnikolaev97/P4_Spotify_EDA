import spotify.application.Spotify;
import spotify.application.SpotifyImpl;
import spotify.application.SpotifyImplTreeMap;
import spotify.records.TopArtists;
import spotify.domain.Playlist;
import spotify.domain.Track;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Java Team :)");
        Playlist playlist1 = new Playlist(1, "playlist 1", "Canciones Chingonas",
                List.of("Ole ole los caracoles", "Esto es un comentario"), LocalDate.of(2020, 1, 1));

        Playlist playlist2 = new Playlist(2, "playlist 2", "Canciones Chingonas",
                List.of("Ole ole los caracoles", "Esto es un comentario"), LocalDate.of(2024, 1, 1));

        Playlist playlist3 = new Playlist(3, "playlist 3", "Canciones Chingonas",
                List.of("Ole ole los caracoles", "Esto es un comentario"), LocalDate.of(2019, 1, 1));

        Track track1 = new Track(1, "Bury the light", List.of("Casey Edwards", "Victor Borba"),
                List.of("Electronical", "Rock"), LocalDate.now(), "Bury_The_Light", 582);

        Track track2 = new Track(2, "Devil Trigger", List.of("Casey Edwards", "Ali Edwards"),
                List.of("Electronical", "Rock"), LocalDate.now(), "Devil_Trigger", 400);

        Track track3 = new Track(3, "The Vengeful One", List.of("Disturbed"),
                List.of("Hard Rock"), LocalDate.now(), "The_Vengeful_One", 252);

        Track track4 = new Track(4, "Bury the light", List.of("Casey Edwards", "Victor Borba"),
                List.of("Electronical", "Rock"), LocalDate.now(), "Bury_The_Light", 582);

        Track track5 = new Track(5, "Devil Trigger", List.of("Casey Edwards", "Ali Edwards"),
                List.of("Electronical", "Rock"), LocalDate.now(), "Devil_Trigger", 400);

        Spotify spotify = new SpotifyImpl();

        spotify.addPlaylist(playlist1);
        spotify.addTrackToPlaylist(playlist1, track2);
        spotify.addTrackToPlaylist(playlist1, track3);
        spotify.addTrackToPlaylist(playlist1, 0, track1);
        //spotify.addTrackToPlaylist(playlist1, track4);
        //spotify.addTrackToPlaylist(playlist1, track5);

        spotify.addPlaylist(playlist2);
        spotify.addTrackToPlaylist(playlist2, track2);
        spotify.addTrackToPlaylist(playlist2, track3);

        spotify.addPlaylist(playlist3);
        spotify.addTrackToPlaylist(playlist3, track1);
        spotify.addTrackToPlaylist(playlist3, track3);
        //sacar las canciones de una lista
        List<Track> tracksPlaylist1 = spotify.getTracks(playlist1);
        System.out.println("Tamaño de la lista " + playlist1.getId() + " : " + tracksPlaylist1.size() + " canciones");
        // tracksPlaylist1.clear();
        tracksPlaylist1.forEach(System.out::println);
        System.out.println();

        //sacar la lista / las listas que tengan
        String genero = "Electronical";
        List<Playlist> playlistsByGenre = spotify.findByGenre(genero);
        System.out.println("Listas que tienen el género  " + genero + ":");
        playlistsByGenre.forEach(System.out::println);
        System.out.println();

        //sacar los artistas con mas canciones en la lista
        List<TopArtists> topArtist = spotify.getTopArtists(playlist1);
        System.out.println("Los / Las artistas con más canciones en la lista es: ");
        topArtist.forEach(System.out::println);
        System.out.println();

        //sacar la canción con mayor duración
        Track longestTrack = spotify.findLongestTrack(playlist1);
        System.out.println("La canción con mayor duración es: " + longestTrack);
        System.out.println();

        //sacar la canción con menor duración
        Track shortestTrack = spotify.findShortestTrack(playlist1);
        System.out.println("La canción con menor duración es: " + shortestTrack);
        System.out.println();

        //sacar el género / los géneros de la lista
        Set<String> genres = spotify.getGenres(playlist1);
        System.out.println("El género / Los géneros de la lista " + playlist1.getId() + " son:");
        genres.forEach(System.out::println);
        System.out.println();

        //sacar el género / los géneros de la lista de forma ordenada
        SortedSet<String> sortedGenres = spotify.getSortedGenres(playlist1);
        System.out.println("El género / Los géneros de la lista " + playlist1.getId() + " son:");
        sortedGenres.forEach(System.out::println);
        System.out.println();

        //sacar las listas que tengan canciones entre las fechas dadas
        LocalDate date1 = LocalDate.of(2000, 1, 1);
        LocalDate date2 = LocalDate.of(2050, 12, 30);
        List<Playlist> playlistByDates = spotify.findByDates(date1, date2);
        System.out.println("Listas que tengan canciones entre: " +
                date1.getDayOfMonth() + "-" + date1.getMonthValue() + "-" + date1.getYear() + " y " +
                date2.getDayOfMonth() + "-" + date2.getMonthValue() + "-" + date2.getYear());
        playlistByDates.forEach(System.out::println);
        System.out.println();
        var allPlaylist = spotify.getAllPlaylist();
        //todas las playlist del hashmap y salen desordenadas porque el hashmap no tiene orden
        System.out.println("playlist de spotify (HashMap)");
        allPlaylist.forEach(playlist -> System.out.println(playlist + System.lineSeparator()));

        ////////////////////////////////////////////////////////

        Spotify spotifyTreeMap = new SpotifyImplTreeMap();
        spotifyTreeMap.addPlaylist(playlist1);
        spotifyTreeMap.addPlaylist(playlist2);
        spotifyTreeMap.addPlaylist(playlist3);

        spotifyTreeMap.addTrackToPlaylist(playlist1, track2);
        spotifyTreeMap.addTrackToPlaylist(playlist1, track3);
        spotifyTreeMap.addTrackToPlaylist(playlist1, 0, track1);
        //spotifyTreeMap.addTrackToPlaylist(playlist1, track4);
        //spotifyTreeMap.addTrackToPlaylist(playlist1, track5);

        spotifyTreeMap.addTrackToPlaylist(playlist2, track2);
        spotifyTreeMap.addTrackToPlaylist(playlist2, track3);

        spotifyTreeMap.addTrackToPlaylist(playlist3, track1);
        spotifyTreeMap.addTrackToPlaylist(playlist3, track3);

        System.out.println("playlist de spotify (TreeMap)");
        var allPlaylist2 = spotifyTreeMap.getAllPlaylist();
        allPlaylist2.forEach(playlist -> System.out.println(playlist + System.lineSeparator()));



    }
}
