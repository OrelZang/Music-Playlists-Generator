package Model;

import Model.AppModel.Logic.DTOClasses.*;
import Model.AppModel.Logic.Exceptions.UserNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface IModel {
    User searchForUserByUsernameAndPassword(User user) throws SQLException, UserNotFoundException;
    Boolean isUsernameAlreadyExistsInDB(User user) throws SQLException;
    User addUserToDB(User user) throws SQLException;
    List<Icon> getAllIcons() throws SQLException;
    void closeAllDBConnections();
    void deletePlaylist(Playlist playlist) throws SQLException;
    PlaylistsStatistics getPlaylistsStatistics(User user,List<Playlist> playlist) throws SQLException;
    List<Playlist> getPlaylistsSongsWithData(User user, List<Playlist> playlistsList) throws  SQLException;
    List<Playlist> getPlaylistsAverages(User user,List<Playlist> playlistsList) throws SQLException;
    ArtistsTags getPlaylistsGenres(User user) throws SQLException;
    ArtistsTags getAllArtistsTagsInDB() throws SQLException;
    List<PlaylistSong> generatePlaylistByPreferences(PlaylistPreferences playlistPreferences) throws SQLException;
    List<PlaylistSong> generatePlaylistBySong(PlaylistPreferences playlistPreferences) throws SQLException;
    void addPlaylistToDB(Playlist playlist) throws SQLException;
    List<Song> getSongsByArtistNameFromDB(Artist artist) throws SQLException;
}
