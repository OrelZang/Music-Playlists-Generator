package Model.AppModel.Database.DatabaseAccess;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.DeleteStatementsCreators.DeleteAllPlaylistSongsStatementCreator;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.DeleteStatementsCreators.DeletePlaylistSongStatementCreator;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.DeleteStatementsCreators.DeletePlaylistStatementCreator;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements.SelectStatementsCreators.*;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.InsertStatementsCreators.InsertPlaylistSongStatementCreator;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.InsertStatementsCreators.InsertUserStatementCreator;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements.DRLStatementExecutor;
import Model.AppModel.Database.IDatabaseAccess;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.DMLStatementExecutor;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.InsertStatementsCreators.InsertPlaylistStatementCreator;
import Model.AppModel.Logic.DTOClasses.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MySQLDatabaseAccess implements IDatabaseAccess {


    @Override
    public void closeAllDBcConnections() {
        DBConnectionsPool dbConnectionsPool = DBConnectionsPool.getInstance();
        dbConnectionsPool.closeAllDBConnections();
    }

    @Override
    public int addUserToDB(User user) throws SQLException {
        InsertUserStatementCreator insertUserStatementCreator = new InsertUserStatementCreator(user);
        List<ISQLStatementCreator> dmlStatementCreatorsList= Collections.singletonList(insertUserStatementCreator);
        return new DMLStatementExecutor(dmlStatementCreatorsList).executeDMLStatement();
    }

    @Override
    public int addPlaylistToDB(Playlist playlist) throws SQLException {
        List<ISQLStatementCreator> dmlStatementCreatorsList= new ArrayList<>();
        InsertPlaylistStatementCreator insertPlaylistStatementCreator = new InsertPlaylistStatementCreator(playlist);
        dmlStatementCreatorsList.add(insertPlaylistStatementCreator);
        List<PlaylistSong> playlistSongList = playlist.getPlaylistSongsList();

        for(PlaylistSong playlistSong: playlistSongList){
            InsertPlaylistSongStatementCreator insertPlaylistSongStatementCreator = new InsertPlaylistSongStatementCreator(playlistSong);
            dmlStatementCreatorsList.add(insertPlaylistSongStatementCreator);
        }

        new DMLStatementExecutor(dmlStatementCreatorsList).executeDMLStatement();

        return DMLStatementExecutor.lastGeneratedKey;
    }

    @Override
    public void addPlaylistSongToDB(PlaylistSong playlistSong) throws SQLException {
        InsertPlaylistSongStatementCreator insertPlaylistSongStatementCreator = new InsertPlaylistSongStatementCreator(playlistSong);
        List<ISQLStatementCreator> dmlStatementCreatorsList= Collections.singletonList(insertPlaylistSongStatementCreator);
        new DMLStatementExecutor(dmlStatementCreatorsList).executeDMLStatement();

    }

    @Override
    public void deletePlaylistSongFromDB(PlaylistSong playlistSong) throws SQLException {
        DeletePlaylistSongStatementCreator deletePlaylistSongStatementCreator = new DeletePlaylistSongStatementCreator(playlistSong);
        List<ISQLStatementCreator> dmlStatementCreatorsList= Collections.singletonList(deletePlaylistSongStatementCreator);
        new DMLStatementExecutor(dmlStatementCreatorsList).executeDMLStatement();
    }

    @Override
    public void deletePlaylistFromDB(Playlist playlist) throws SQLException {
        DeleteAllPlaylistSongsStatementCreator deleteAllPlaylistSongsStatementCreator = new DeleteAllPlaylistSongsStatementCreator(playlist);
        DeletePlaylistStatementCreator deletePlaylistSongStatementCreator = new DeletePlaylistStatementCreator(playlist);
        List<ISQLStatementCreator> dmlStatementCreatorsList= Arrays.asList(deleteAllPlaylistSongsStatementCreator,deletePlaylistSongStatementCreator);
        new DMLStatementExecutor(dmlStatementCreatorsList).executeDMLStatement();
    }

    @Override
    public Boolean isUsernameAlreadyExistsInDB(User user) throws SQLException{
        SelectUserByUsernameStaetementCreator selectUserByUsernameStaetementCreator = new SelectUserByUsernameStaetementCreator(user);
        return new DRLStatementExecutor(selectUserByUsernameStaetementCreator).isUsernameAlreadyExistsInDB();
    }

    @Override
    public User searchForUserByUsernameAndPasswordInDB(User user) throws SQLException {
        SelectUserStatementCreator selectUserStatementCreator = new SelectUserStatementCreator(user);
        return new DRLStatementExecutor(selectUserStatementCreator).searchUserInDB();
    }

    @Override
    public List<Playlist> getUserPlaylistsFromDB(User user) throws SQLException {
        SelectPlaylistsStatementCreator selectPlaylistsStatementCreator = new SelectPlaylistsStatementCreator(user);
        return new DRLStatementExecutor(selectPlaylistsStatementCreator).getUserPlaylistsFromDB();
    }

    @Override
    public List<PlaylistSong> getPlaylistSongsFromDB(Playlist playlist) throws SQLException {
        SelectPlaylistSongsStatementCreator selectPlaylistSongsStatementCreator = new SelectPlaylistSongsStatementCreator(playlist);
        return new DRLStatementExecutor(selectPlaylistSongsStatementCreator).getPlaylistSongsFromDB();
    }

    @Override
    public ArtistsTags getAllArtistsTagsInDB() throws SQLException {
        SelectTagsStatementCreator selectTagsStatementCreator = new SelectTagsStatementCreator();
        return new DRLStatementExecutor(selectTagsStatementCreator).getArtistsTags();
    }

    @Override
    public List<Icon> getAllIconsInDB() throws SQLException {
        SelectIconsStatementCreator selectIconsStatementCreator = new SelectIconsStatementCreator();
        return new DRLStatementExecutor(selectIconsStatementCreator).getIcons();
    }

    @Override
    public List<Song> getSongsByArtistNameFromDB(Artist artist) throws SQLException {
        SelectSongsByArtistStatementCreator selectSongsByArtistStatementCreator=new SelectSongsByArtistStatementCreator(artist);
        return new DRLStatementExecutor(selectSongsByArtistStatementCreator).getSongs();
    }

    @Override
    public List<Song> getCandidateRecommendedSongsBasedOnGivenSongFromDB(Song song) throws SQLException {
        SelectCandidateRecommendedSongsBasedOnSongStatementCreator selectCandidateRecommendedSongsBasedOnSongStatementCreator = new SelectCandidateRecommendedSongsBasedOnSongStatementCreator(song);
        return new DRLStatementExecutor(selectCandidateRecommendedSongsBasedOnSongStatementCreator).getSongs();
    }

    @Override
    public List<Playlist> getPlaylistsSongsWithDataFromDB(User user, List<Playlist> playlistsList) throws SQLException {
        SelectPlaylistSongsDataStatementCreator selectPlaylistSongsDataStatementCreator = new SelectPlaylistSongsDataStatementCreator(user);
        return new DRLStatementExecutor(selectPlaylistSongsDataStatementCreator).getPlaylistsSongsWithData(playlistsList);
    }

    @Override
    public List<Playlist> getPlaylistsAveragesFromDB(User user, List<Playlist> playlistsList) throws SQLException {
        SelectPlaylistAveragesStatementCreator selectPlaylistAveragesStatementCreator = new SelectPlaylistAveragesStatementCreator(user);
        return new DRLStatementExecutor(selectPlaylistAveragesStatementCreator).getPlaylistsAverages(playlistsList);
    }

    @Override
    public ArtistsTags getPlaylistsGenresFromDB(User user) throws SQLException {
        SelectPlaylistsGenresStatementCreator selectPlaylistsGenresStatementCreator = new SelectPlaylistsGenresStatementCreator(user);
        return new DRLStatementExecutor(selectPlaylistsGenresStatementCreator).getPlaylistsGenres();
    }

    @Override
    public List<Song> getSongsByGenreAndModeFromDB(PlaylistPreferences playlistPreferences) throws SQLException {
        SelectSongsByGenreStatementCreator selectPlaylistsGenresStatementCreator = new SelectSongsByGenreStatementCreator(playlistPreferences);
        return new DRLStatementExecutor(selectPlaylistsGenresStatementCreator).getSongsByGenreAndMode();
    }


}
