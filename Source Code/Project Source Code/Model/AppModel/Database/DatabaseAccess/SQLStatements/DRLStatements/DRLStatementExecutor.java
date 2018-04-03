package Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements;

import Model.AppModel.Database.DatabaseAccess.DBConnectionsPool;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.SQLStatementExecutor;
import Model.AppModel.Logic.DTOClasses.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantColumnNames.*;

public class DRLStatementExecutor extends SQLStatementExecutor {
    private ISQLStatementCreator sqlStatementCreator;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public DRLStatementExecutor(ISQLStatementCreator sqlStatementCreator) {
        this.sqlStatementCreator = sqlStatementCreator;
    }

    private ResultSet executeQueryStatement() throws SQLException {
        Boolean isErrorOccurred=false;
        connection=null;
        ResultSet resultSet = null;
        preparedStatement=null;

        try {
            DBConnectionsPool dbConnectionsPool = DBConnectionsPool.getInstance();
            connection = dbConnectionsPool.getConnection();
            preparedStatement = sqlStatementCreator.createSQLStatement(connection);
            resultSet = preparedStatement.executeQuery();



        } catch (SQLException | ClassNotFoundException e) {
            isErrorOccurred=true;
        }

        if(isErrorOccurred){
            throw new SQLException(sqlStatementCreator.getExceptionStringForStatement());
        }

        return resultSet;

    }





    public User searchUserInDB() throws SQLException {
        User userToReturn=null;
        ResultSet resultSet =executeQueryStatement();

        if(resultSet.next()){

            int userID = resultSet.getInt(USER_ID_COLUMN_NAME);
            String username = resultSet.getString(USER_USERNAME_COLUMN_NAME);
            String password = resultSet.getString(USER_PASSWORD_COLUMN_NAME);
            String name = resultSet.getString(USER_NAME_COLUMN_NAME);

            int userIconID=resultSet.getInt(USER_ICON_ID_COLUMN_NAME);
            String iconFileName = resultSet.getString(ICON_FILE_NAME_COLUMN_NAME);
            String iconName = resultSet.getString(ICON_NAME_COLUMN_NAME);
            userToReturn= new User(userID,username,password,name,new Icon(userIconID,iconFileName,iconName));

        }

        super.closeDBResources(connection, Collections.singletonList(preparedStatement),resultSet);
        return userToReturn;

    }

    public List<Playlist> getUserPlaylistsFromDB() throws SQLException {
        List<Playlist> userPlaylists = new ArrayList<>();
        ResultSet resultSet =executeQueryStatement();

        while(resultSet.next()){
            int playlistID = resultSet.getInt(PLAYLIST_ID_COLUMN_NAME);
            int userID = resultSet.getInt(USER_ID_COLUMN_NAME);
            String playlistName = resultSet.getString(PLAYLIST_NAME_COLUMN_NAME);



            userPlaylists.add(new Playlist(playlistID,userID,playlistName));

        }

        super.closeDBResources(connection, Collections.singletonList(preparedStatement),resultSet);
        return userPlaylists;

    }

    public List<PlaylistSong> getPlaylistSongsFromDB() throws SQLException {
        List<PlaylistSong> playlistSongs = new ArrayList<>();
        ResultSet resultSet =executeQueryStatement();

        while(resultSet.next()){
            int playlistID = resultSet.getInt(PLAYLIST_ID_COLUMN_NAME);
            String songID = resultSet.getString(SONG_ID_COLUMN_NAME);
            String songName = resultSet.getString(SONG_NAME_COLUMN_NAME);
            int songYear=resultSet.getInt(SONG_YEAR_COLUMN_NAME);
            String albumName = resultSet.getString(SONG_ALBUM_NAME_COLUMN_NAME);
            String artistName = resultSet.getString(ARTIST_NAME_COLUMN_NAME);
            String artistID = resultSet.getString(ARTIST_ID_COLUMN_NAME);
            float songDuration = resultSet.getFloat(SONG_DURATION_COLUMN_NAME);

            playlistSongs.add(new PlaylistSong(playlistID,songID,songName,artistID,artistName,albumName,songYear,songDuration));

        }

        super.closeDBResources(connection, Collections.singletonList(preparedStatement),resultSet);
        return playlistSongs;

    }

    public Boolean isUsernameAlreadyExistsInDB() throws SQLException {
        Boolean isUsernameFound;
        ResultSet resultSet =executeQueryStatement();

        if(resultSet.next()){
            isUsernameFound=true;
        }else{
            isUsernameFound=false;
        }

        super.closeDBResources(connection, Collections.singletonList(preparedStatement),resultSet);
        return isUsernameFound;
    }

    public ArtistsTags getArtistsTags() throws SQLException {
        ArtistsTags artistsTags = new ArtistsTags();
        ResultSet resultSet =executeQueryStatement();

        while(resultSet.next()){
            artistsTags.addArtistTag(resultSet.getString(TAG_NAME_COLUMN_NAME));
        }

        super.closeDBResources(connection, Collections.singletonList(preparedStatement),resultSet);
        return artistsTags;
    }

    public List<Icon> getIcons() throws SQLException {
        List<Icon> iconsList = new ArrayList<>();
        ResultSet resultSet =executeQueryStatement();

        while(resultSet.next()){
           int iconID= resultSet.getInt(ICON_ID_COLUMN_NAME);
           String iconFileName = resultSet.getString(ICON_FILE_NAME_COLUMN_NAME);
            String iconName = resultSet.getString(ICON_NAME_COLUMN_NAME);
           iconsList.add(new Icon(iconID,iconFileName,iconName));
        }

        super.closeDBResources(connection, Collections.singletonList(preparedStatement),resultSet);
        return iconsList;
    }

    public List<Song> getSongs() throws SQLException {
        List<Song> songsList = new ArrayList<>();
        ResultSet resultSet =executeQueryStatement();

        while(resultSet.next()){
            String songID=resultSet.getString(SONG_ID_COLUMN_NAME);
            String name=resultSet.getString(SONG_NAME_COLUMN_NAME);
            String album=resultSet.getString(SONG_ALBUM_NAME_COLUMN_NAME);
            int year=resultSet.getInt(SONG_YEAR_COLUMN_NAME);
            float tempo=resultSet.getFloat(SONG_TEMPO_COLUMN_NAME);
            float danceability=resultSet.getFloat(SONG_DANCEABILITY_COLUMN_NAME);
            float duration=resultSet.getFloat(SONG_DURATION_COLUMN_NAME);
            float energy=resultSet.getFloat(SONG_ENERGY_COLUMN_NAME);
            int key = resultSet.getInt(SONG_KEY_COLUMN_NAME);
            float loudness=resultSet.getFloat(SONG_LOUDNESS_COLUMN_NAME);
            int mode = resultSet.getInt(SONG_MODE_COLUMN_NAME);
            int timeSignature = resultSet.getInt(SONG_TIME_SIGNATURE_COLUMN_NAME);

            String artistID=resultSet.getString(SONG_ARTIST_COLUMN_NAME);
            String artistName=resultSet.getString(ARTIST_NAME_COLUMN_NAME);
            float familiarity=resultSet.getFloat(ARTIST_FAMILIARITY_COLUMN_NAME);
            Artist artist = new Artist(artistID,artistName,familiarity);
            songsList.add(new Song(songID,name,artist,album,year,tempo,danceability,duration,energy,key,loudness,mode,timeSignature));
        }

        super.closeDBResources(connection, Collections.singletonList(preparedStatement),resultSet);
        return songsList;
    }

    public List<Playlist> getPlaylistsSongsWithData(List<Playlist> playlistsList) throws  SQLException{
        ResultSet resultSet =executeQueryStatement();

        while(resultSet.next()) {
            String songID=resultSet.getString(SONG_ID_COLUMN_NAME);
            int playlistID = resultSet.getInt(PLAYLIST_ID_COLUMN_NAME);

            for (Playlist playlist : playlistsList) {
                List<PlaylistSong> playlistSongsList = playlist.getPlaylistSongsList();
                if(playlist.getPlaylistId()==playlistID)
                    for (PlaylistSong playlistSong : playlistSongsList) {

                        if(playlistSong.getSongID().equals(songID)){
                            playlistSong.setTempo(resultSet.getFloat(SONG_TEMPO_COLUMN_NAME));
                            playlistSong.setLoudness(resultSet.getFloat(SONG_LOUDNESS_COLUMN_NAME));
                        }


                    }
            }
        }

        super.closeDBResources(connection, Collections.singletonList(preparedStatement),resultSet);

        return playlistsList;
    }

    public List<Playlist> getPlaylistsAverages(List<Playlist> playlistsList) throws  SQLException{
        ResultSet resultSet =executeQueryStatement();

        while(resultSet.next()) {
            int playlistID = resultSet.getInt(PLAYLIST_ID_COLUMN_NAME);

            for (Playlist playlist : playlistsList) {
                if(playlist.getPlaylistId()==playlistID){
                    playlist.setAverageTempo(resultSet.getFloat(AVERAGE_TEMPO_COLUMN_NAME));
                    playlist.setAverageLoudness(resultSet.getFloat(AVERAGE_LOUDNESS_COLUMN_NAME));
                    playlist.setAverageFamiliarity(resultSet.getFloat(AVERAGE_FAMILIARITY_COLUMN_NAME));
                }


            }
        }

        super.closeDBResources(connection, Collections.singletonList(preparedStatement),resultSet);

        return playlistsList;
    }

    public ArtistsTags getPlaylistsGenres() throws  SQLException{
        ResultSet resultSet =executeQueryStatement();

        ArtistsTags artistsTags = new ArtistsTags();
        while(resultSet.next()) {
            artistsTags.addArtistTag(resultSet.getString(ARTIST_TAG_NAME_COLUMN_NAME));
        }

        super.closeDBResources(connection, Collections.singletonList(preparedStatement),resultSet);

        return artistsTags;
    }

    public List<Song> getSongsByGenreAndMode() throws  SQLException{
        List<Song> songsList = new ArrayList<>();
        ResultSet resultSet =executeQueryStatement();

        while(resultSet.next()){
            String songID=resultSet.getString(SONG_ID_COLUMN_NAME);
            String name=resultSet.getString(SONG_NAME_COLUMN_NAME);
            String album=resultSet.getString(SONG_ALBUM_NAME_COLUMN_NAME);
            int year=resultSet.getInt(SONG_YEAR_COLUMN_NAME);
            float tempo=resultSet.getFloat(SONG_TEMPO_COLUMN_NAME);
            float danceability=resultSet.getFloat(SONG_DANCEABILITY_COLUMN_NAME);
            float duration=resultSet.getFloat(SONG_DURATION_COLUMN_NAME);
            float energy=resultSet.getFloat(SONG_ENERGY_COLUMN_NAME);
            int key = resultSet.getInt(SONG_KEY_COLUMN_NAME);
            float loudness=resultSet.getFloat(SONG_LOUDNESS_COLUMN_NAME);
            int mode = resultSet.getInt(SONG_MODE_COLUMN_NAME);
            int timeSignature = resultSet.getInt(SONG_TIME_SIGNATURE_COLUMN_NAME);

            String artistID=resultSet.getString(SONG_ARTIST_COLUMN_NAME);
            String artistName=resultSet.getString(ARTIST_NAME_COLUMN_NAME);
            float familiarity=resultSet.getFloat(ARTIST_FAMILIARITY_COLUMN_NAME);
            Artist artist = new Artist(artistID,artistName,familiarity);
            songsList.add(new Song(songID,name,artist,album,year,tempo,danceability,duration,energy,key,loudness,mode,timeSignature));
    }

        super.closeDBResources(connection, Collections.singletonList(preparedStatement),resultSet);
        return songsList;
    }


}
