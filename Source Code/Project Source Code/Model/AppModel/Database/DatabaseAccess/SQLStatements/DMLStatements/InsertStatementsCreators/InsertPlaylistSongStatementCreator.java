package Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.InsertStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.DMLStatementExecutor;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.PlaylistSong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.INSERT_PLAYLIST_SONG_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.INSERT_PLAYLIST_SONG_PREPARED_STATEMENT;

public class InsertPlaylistSongStatementCreator implements ISQLStatementCreator {
    private PlaylistSong playlistSongToInsert;

    public InsertPlaylistSongStatementCreator(PlaylistSong playlistSongToInsert) {
        this.playlistSongToInsert = playlistSongToInsert;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement insertPlaylistSongStatement =connection.prepareStatement(INSERT_PLAYLIST_SONG_PREPARED_STATEMENT);
        insertPlaylistSongStatement.setInt(1, DMLStatementExecutor.lastGeneratedKey);
        insertPlaylistSongStatement.setString(2,playlistSongToInsert.getSongID());
        return insertPlaylistSongStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return INSERT_PLAYLIST_SONG_STATEMENT_EXCEPTION;
    }
}
