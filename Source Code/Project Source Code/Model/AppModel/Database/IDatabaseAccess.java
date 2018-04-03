package Model.AppModel.Database;

import Model.AppModel.Logic.DTOClasses.*;

import java.sql.SQLException;
import java.util.List;

public interface IDatabaseAccess {
    void closeAllDBcConnections();

    int addUserToDB(User user) throws SQLException;
    int addPlaylistToDB(Playlist playlist) throws SQLException;
    void addPlaylistSongToDB(PlaylistSong playlistSong) throws SQLException;

    void deletePlaylistSongFromDB(PlaylistSong playlistSong) throws SQLException;
    void deletePlaylistFromDB(Playlist playlist) throws SQLException;

    Boolean isUsernameAlreadyExistsInDB(User user) throws SQLException;
    User searchForUserByUsernameAndPasswordInDB(User user) throws SQLException;
    List<Playlist> getUserPlaylistsFromDB(User user) throws SQLException;
    List<PlaylistSong> getPlaylistSongsFromDB(Playlist playlist) throws SQLException;
    ArtistsTags getAllArtistsTagsInDB() throws SQLException;
    List<Icon> getAllIconsInDB() throws SQLException;
    List<Song> getSongsByArtistNameFromDB(Artist artist) throws SQLException;
    List<Song> getCandidateRecommendedSongsBasedOnGivenSongFromDB(Song song) throws SQLException;
    List<Playlist> getPlaylistsSongsWithDataFromDB(User user, List<Playlist> playlistsList) throws  SQLException;
    List<Playlist> getPlaylistsAveragesFromDB(User user, List<Playlist> playlistsList) throws  SQLException;
    ArtistsTags getPlaylistsGenresFromDB(User user) throws  SQLException;
    List<Song> getSongsByGenreAndModeFromDB(PlaylistPreferences playlistPreferences) throws SQLException;

}
