package Controller.AppController;

import Controller.IController;
import Model.AppModel.Logic.DTOClasses.*;
import Model.AppModel.Logic.Exceptions.UserNotFoundException;
import Model.AppModel.Logic.Exceptions.UsernameAlreadyExistsException;
import Model.IModel;

import java.sql.SQLException;
import java.util.List;

import static Model.AppModel.Logic.Exceptions.ConstantExceptionStrings.USERNAME_ALREADY_EXISTS_EXCEPTION_STRING;

public class AppController implements IController {
    private IModel model;

    public AppController(IModel model) {
        this.model = model;
    }


    @Override
    public void closeAllDBConnections() {
        model.closeAllDBConnections();
    }

    @Override
    public User searchForUserByUsernameAndPassword(User user) throws SQLException, UserNotFoundException {
        return model.searchForUserByUsernameAndPassword(user);
    }

    @Override
    public List<Icon> getAllIcons() throws SQLException {
        return model.getAllIcons();
    }

    @Override
    public User addNewUser(User user) throws SQLException, UsernameAlreadyExistsException {
        if (model.isUsernameAlreadyExistsInDB(user)){
            throw new UsernameAlreadyExistsException(USERNAME_ALREADY_EXISTS_EXCEPTION_STRING);
        }

        return model.addUserToDB(user);
    }

    @Override
    public void deletePlaylist(Playlist playlist) throws SQLException {
        model.deletePlaylist(playlist);
    }

    @Override
    public PlaylistsStatistics getPlaylistsStatistics(User user,List<Playlist> playlistsList) throws SQLException {
        return model.getPlaylistsStatistics(user,playlistsList);
    }

    @Override
    public List<Playlist> getPlaylistsSongsWithData(User user, List<Playlist> playlistsList) throws SQLException {
        return model.getPlaylistsSongsWithData(user,playlistsList);
    }

    @Override
    public ArtistsTags getAllArtistsTagsInDB() throws SQLException {
        return model.getAllArtistsTagsInDB();
    }

    @Override
    public List<PlaylistSong> generatePlaylistByPreferences(PlaylistPreferences playlistPreferences) throws SQLException {
        return model.generatePlaylistByPreferences(playlistPreferences);
    }

    @Override
    public List<PlaylistSong> generatePlaylistBySong(PlaylistPreferences playlistPreferences) throws SQLException {
        return model.generatePlaylistBySong(playlistPreferences);
    }

    @Override
    public void addPlaylistToDB(Playlist playlist) throws SQLException {
        model.addPlaylistToDB(playlist);
    }

    @Override
    public List<Song> getSongsByArtistNameFromDB(Artist artist) throws SQLException {
        return model.getSongsByArtistNameFromDB(artist);
    }

}
