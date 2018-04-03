package Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements.SelectStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.PlaylistPreferences;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.SELECT_SONGS_BY_ARTIST_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.SELECT_SONG_BY_GENRE_PREPARED_STATEMENT;

public class SelectSongsByGenreStatementCreator implements ISQLStatementCreator {
    private PlaylistPreferences playlistPreferences;

    public SelectSongsByGenreStatementCreator(PlaylistPreferences playlistPreferences) {
        this.playlistPreferences = playlistPreferences;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement selectSongsByGenreStatement =connection.prepareStatement(SELECT_SONG_BY_GENRE_PREPARED_STATEMENT);
        selectSongsByGenreStatement.setString(1, playlistPreferences.getGenre());
        if(playlistPreferences.getHappines()>=0){
            selectSongsByGenreStatement.setInt(2,1);
        }else{
            selectSongsByGenreStatement.setInt(2,0);
        }
        return selectSongsByGenreStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return SELECT_SONGS_BY_ARTIST_STATEMENT_EXCEPTION;
    }
}
