package Controller;

import Model.AppModel.Logic.DTOClasses.*;
import Model.AppModel.Logic.Exceptions.UserNotFoundException;
import Model.AppModel.Logic.Exceptions.UsernameAlreadyExistsException;

import java.sql.SQLException;
import java.util.List;

public interface IController {
    void closeAllDBConnections();
    User searchForUserByUsernameAndPassword(User user) throws SQLException, UserNotFoundException;
    List<Icon> getAllIcons() throws SQLException;
    User addNewUser(User user) throws SQLException, UsernameAlreadyExistsException;
    void deletePlaylist(Playlist playlist) throws SQLException;
    PlaylistsStatistics getPlaylistsStatistics(User user,List<Playlist> playlistsList) throws SQLException;
    List<Playlist> getPlaylistsSongsWithData(User user, List<Playlist> playlistsList) throws SQLException;
    ArtistsTags getAllArtistsTagsInDB() throws SQLException;
    List<PlaylistSong> generatePlaylistByPreferences(PlaylistPreferences playlistPreferences) throws SQLException;
    List<PlaylistSong> generatePlaylistBySong(PlaylistPreferences playlistPreferences) throws SQLException;
    void addPlaylistToDB(Playlist playlist) throws SQLException;
    List<Song> getSongsByArtistNameFromDB(Artist artist) throws SQLException;
}
